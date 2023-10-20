package com.fz.mall.order.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSON;
import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fz.mall.api.cart.dto.CartItemDTO;
import com.fz.mall.api.cart.feign.CartFeignClient;
import com.fz.mall.api.dto.LockStockDTO;
import com.fz.mall.api.dto.SkuQuantityDTO;
import com.fz.mall.api.feign.StockFeignClient;
import com.fz.mall.api.order.constant.OrderStatus;
import com.fz.mall.api.user.dto.MemberReceiveAddressDTO;
import com.fz.mall.api.user.feign.MemberFeignClient;
import com.fz.mall.common.context.ContextHolder;
import com.fz.mall.common.database.util.PageUtil;
import com.fz.mall.common.exception.MallServerException;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.common.rabbitmq.constant.OrderMqConstants;
import com.fz.mall.common.rabbitmq.constant.StockMqConstants;
import com.fz.mall.common.rabbitmq.dto.SeckillOrderDTO;
import com.fz.mall.common.redis.pojo.SeckillSkuCache;
import com.fz.mall.common.resp.ResponseEnum;
import com.fz.mall.common.resp.ServerResponseEntity;
import com.fz.mall.order.constant.OrderConstants;
import com.fz.mall.order.entity.Order;
import com.fz.mall.order.entity.OrderItem;
import com.fz.mall.order.entity.PaymentInfo;
import com.fz.mall.order.mapper.OrderMapper;
import com.fz.mall.order.pojo.dto.PaySyncResultDTO;
import com.fz.mall.order.pojo.param.OrderSubmitParam;
import com.fz.mall.order.pojo.vo.OrderInfoVO;
import com.fz.mall.order.pojo.vo.OrderItemInfoVO;
import com.fz.mall.order.pojo.vo.OrderListItemVO;
import com.fz.mall.order.pojo.vo.OrderSubmitResponseVO;
import com.fz.mall.order.service.OrderItemService;
import com.fz.mall.order.service.OrderSeckillService;
import com.fz.mall.order.service.OrderService;
import com.fz.mall.order.service.PaymentInfoService;
import com.fz.mall.order.util.AlipayUtil;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Slf4j
@Service
@AllArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    private ThreadPoolExecutor executor;

    private StringRedisTemplate redisTemplate;

    private RabbitTemplate rabbitTemplate;

    private OrderItemService orderItemService;

    private PaymentInfoService paymentInfoService;

    private MemberFeignClient memberFeignClient;

    private CartFeignClient cartFeignClient;

    private StockFeignClient stockFeignClient;

    private OrderSeckillService orderSeckillService;

    private PlatformTransactionManager transactionManager;

    private static final DefaultRedisScript<Long> ORDER_TOKEN_SCRIPT;

    static {
        ORDER_TOKEN_SCRIPT = new DefaultRedisScript<>("if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end", Long.class);
    }

    public OrderInfoVO getOrderInfo() {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        OrderInfoVO orderInfoVO = new OrderInfoVO();
        CompletableFuture<Void> addressFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            ServerResponseEntity<List<MemberReceiveAddressDTO>> userAddress = memberFeignClient.getCurrentUserAddress();
            List<MemberReceiveAddressDTO> addresses = userAddress.getData();
            orderInfoVO.setMemberAddressVos(addresses);
        }, executor);


        CompletableFuture<Void> cartFuture = CompletableFuture.runAsync(() -> {

            RequestContextHolder.setRequestAttributes(requestAttributes);
            ServerResponseEntity<List<CartItemDTO>> userCartItemsResp = cartFeignClient.getUserCheckedCartItems();
            Map<Long, Boolean> hasStockMap = new HashMap<>();
            List<OrderItemInfoVO> orderItemInfoVOS = userCartItemsResp.getData().stream().map((item) -> {
                OrderItemInfoVO orderItemInfoVo = new OrderItemInfoVO();
                BeanUtils.copyProperties(item, orderItemInfoVo);
                hasStockMap.put(orderItemInfoVo.getSkuId(), orderItemInfoVo.getHasStock());
                return orderItemInfoVo;
            }).collect(Collectors.toList());
            orderInfoVO.setItems(orderItemInfoVOS);
            orderInfoVO.setStocks(hasStockMap);
        }, executor);

        CompletableFuture.allOf(addressFuture, cartFuture).join();

        String orderToken = IdUtil.fastSimpleUUID();
        redisTemplate.opsForValue().set(OrderConstants.ORDER_TOKEN_KEY + ContextHolder.getUser().getUid(), orderToken);
        orderInfoVO.setOrderToken(orderToken);

        return orderInfoVO;
    }


    private OrderSubmitResponseVO handleGenerateOrder(String orderSn, List<OrderItem> orderItems, MemberReceiveAddressDTO destAddr) {

        OrderSubmitResponseVO orderSubmitResponseVO = new OrderSubmitResponseVO();
        Order order = new Order();

        order.setOrderSn(orderSn);
        // 运费
        order.setFreightAmount(new BigDecimal(0));
        // 收货地址
        order.setReceiverCity(destAddr.getCity());
        order.setReceiverDetailAddress(destAddr.getDetailAddress());
        order.setReceiverName(destAddr.getName());
        order.setReceiverPhone(destAddr.getPhone());
        order.setReceiverPostCode(destAddr.getPostCode());
        order.setReceiverProvince(destAddr.getProvince());
        order.setReceiverRegion(destAddr.getRegion());

        // 订单总额
        BigDecimal orderTotalAmount = orderItems.stream().map(OrderItem::getRealAmount).reduce(new BigDecimal(0), BigDecimal::add);

        // 订单总额（不含运费）
        order.setTotalAmount(orderTotalAmount);
        // 总额（含运费）
        order.setPayAmount(orderTotalAmount.add(order.getFreightAmount()));
        // 订单项各种优惠的累计  这里简化了
        order.setCouponAmount(new BigDecimal(0));
        order.setPromotionAmount(new BigDecimal(0));
        order.setIntegrationAmount(new BigDecimal(0));
        // 叠加积分
        order.setIntegration(0);
        // 叠加成长值
        order.setGrowth(0);
        order.setStatus(OrderStatus.NEW.getCode());
        order.setAutoConfirmDay(7);
        order.setDeleteStatus(0);

        // 验价比较和前端传入的价格
//        if (Math.abs(order.getPayAmount().subtract(orderSubmitParam.getPayPrice()).doubleValue()) > 0.01) {
//
//        }

        order.setCreateTime(LocalDateTime.now());
        order.setModifyTime(LocalDateTime.now());

        order.setMemberId(ContextHolder.getUser().getUid());

        // 保存订单项
        orderItemService.saveBatch(orderItems);
        // 保存订单
        save(order);

        orderSubmitResponseVO.setOrder(order);
        return orderSubmitResponseVO;

    }

    /**
     * 锁定库存，锁定成功返回商品信息
     *
     * @param orderSn
     */
    private List<OrderItem> lockStock(String orderSn) {

        // 获取购物车选中商品信息
        ServerResponseEntity<List<CartItemDTO>> userCheckedCartItems = cartFeignClient.getUserCheckedCartItems();
        List<CartItemDTO> cartItems = userCheckedCartItems.getData();

        List<OrderItem> orderItems = cartItems.stream().map((cartItem) -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderSn(orderSn);
            orderItem.setSkuName(cartItem.getTitle());
            orderItem.setSkuId(cartItem.getSkuId());
            orderItem.setSkuPic(cartItem.getImage());
            orderItem.setSkuQuantity(cartItem.getCount());
            orderItem.setSkuAttrsVals(String.join(";", cartItem.getSkuAttrValues()));
            orderItem.setSkuPrice(cartItem.getPrice());
            orderItem.setCouponAmount(new BigDecimal(0));
            orderItem.setPromotionAmount(new BigDecimal(0));
            orderItem.setIntegrationAmount(new BigDecimal(0));
            BigDecimal finalPrice = cartItem.getTotalPrice().subtract(orderItem.getCouponAmount()).subtract(orderItem.getPromotionAmount()).subtract(orderItem.getIntegrationAmount());
            orderItem.setRealAmount(finalPrice);
            orderItem.setSpuName("");
            orderItem.setSpuBrand("");
            return orderItem;
        }).collect(Collectors.toList());

        List<SkuQuantityDTO> skuQuantityDTOS = orderItems.stream().map((item) -> {
            SkuQuantityDTO skuQuantityDTO = new SkuQuantityDTO();
            skuQuantityDTO.setQuantity(item.getSkuQuantity());
            skuQuantityDTO.setSkuId(item.getSkuId());
            return skuQuantityDTO;
        }).collect(Collectors.toList());

        // 锁定库存
        try {
            LockStockDTO lockStockDTO = new LockStockDTO();
            lockStockDTO.setOrderSn(orderSn);

            lockStockDTO.setSkuQuantityDTOS(skuQuantityDTOS);
            ServerResponseEntity serverResponseEntity = stockFeignClient.updateStockLock(lockStockDTO);
            if (!serverResponseEntity.getSuccess()) {
                log.error(serverResponseEntity.getMsg());
                // 执行失败抛出异常 订单回滚
                throw new MallServerException(ResponseEnum.getResponseEnumByCode(serverResponseEntity.getCode()));
            }
        } catch (Exception e) {
            rabbitTemplate.convertAndSend(StockMqConstants.STOCK_EXCHANGE, StockMqConstants.UNLOCK_STOCK_ROUTING_KEY, orderSn);
            throw new MallServerException(ResponseEnum.SERVER_INTERNAL_ERROR);
        }
        return orderItems;
    }


    private MemberReceiveAddressDTO getUserAddrInfo(Long addrId) {

        if (ObjectUtils.isEmpty(addrId)) {
            throw new MallServerException(ResponseEnum.DELIVERY_ADDRESS_INVALID);
        }
        ServerResponseEntity<MemberReceiveAddressDTO> destAddrResp = memberFeignClient.getAddressById(addrId);
        return destAddrResp.getData();
    }

    /**
     * 使用MQ保证最终一致性
     *
     * @param orderSubmitParam
     * @return
     */
    @Override
    public OrderSubmitResponseVO tryGenerateOrder(OrderSubmitParam orderSubmitParam) {

        // 校验订单token
        checkOrderToken(orderSubmitParam.getOrderToken());
        // 生成订单id
        String orderSn = IdWorker.getTimeId();
        // 锁库存
        List<OrderItem> orderItems = lockStock(orderSn);
        // 获取用户地址信息
        MemberReceiveAddressDTO destAddr = getUserAddrInfo(orderSubmitParam.getAddrId());

        OrderSubmitResponseVO orderSubmitResponseVO;

        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus transactionStatus = transactionManager.getTransaction(definition);
        try {
            orderSubmitResponseVO = handleGenerateOrder(orderSn, orderItems, destAddr);
            transactionManager.commit(transactionStatus);
        } catch (Throwable e) {
            transactionManager.rollback(transactionStatus);
            throw e;
        }

        // 发送延迟消息 一分钟后关闭订单
        Message message = MessageBuilder
                .withBody(JSON.toJSONBytes(orderSn))
                .setHeader(MessageProperties.X_DELAY, OrderMqConstants.ORDER_CLOSE_DELAY_TIME)
                .build();

        rabbitTemplate.convertAndSend(OrderMqConstants.ORDER_EXCHANGE, OrderMqConstants.ORDER_CLOSE_ROUTING_KEY, message);
        // 发送订单创建成功消息  清空购物车选中项
        rabbitTemplate.convertAndSend(OrderMqConstants.ORDER_EXCHANGE, OrderMqConstants.ORDER_CREATE_ROUTING_KEY, ContextHolder.getUser().getUid());


        return orderSubmitResponseVO;
    }

    @Override
    public OrderSubmitResponseVO submitSeckillOrder(OrderSubmitParam orderSubmitParam) {

        checkOrderToken(orderSubmitParam.getOrderToken());

        OrderSubmitResponseVO orderSubmitResponseVO = new OrderSubmitResponseVO();
        Order order = new Order();
        order.setOrderSn(IdWorker.getTimeId());
        // TODO 检查地址是否正确  计算真实价格

        SeckillSkuCache seckillSkuCache = orderSeckillService.seckill(orderSubmitParam.getSeckillSessionId(), orderSubmitParam.getSeckillSkuId(), orderSubmitParam.getSeckillCode(), orderSubmitParam.getSeckillQuantity());

        SeckillOrderDTO seckillOrderDTO = new SeckillOrderDTO();
        seckillOrderDTO.setOrderSn(order.getOrderSn());
        seckillOrderDTO.setUserId(ContextHolder.getUser().getUid());
        seckillOrderDTO.setCount(orderSubmitParam.getSeckillQuantity());

        BeanUtils.copyProperties(seckillSkuCache, seckillOrderDTO);
        order.setPayAmount(seckillOrderDTO.getSeckillPrice().multiply(new BigDecimal(seckillOrderDTO.getCount())));
        orderSubmitResponseVO.setOrder(order);

        // 异步创建订单
        rabbitTemplate.convertAndSend(OrderMqConstants.ORDER_EXCHANGE, OrderMqConstants.ORDER_SECKILL_CREATE_ROUTING_KEY, seckillOrderDTO);

        return orderSubmitResponseVO;
    }

    /**
     * 校验订单token
     *
     * @param token
     */
    private void checkOrderToken(String token) {
        String orderTokenKey = OrderConstants.ORDER_TOKEN_KEY + ContextHolder.getUser().getUid();
        //lua原子验证令牌 防止并发问题
        Long result = redisTemplate.execute(ORDER_TOKEN_SCRIPT, Collections.singletonList(orderTokenKey), token);
        if (Optional.ofNullable(result).orElse(0L) == 0L) {
            throw new MallServerException("令牌验证失败");
        }
    }

    @Override
    public void closeOrder(String orderSn) {
        Order order = lambdaQuery().select(Order::getStatus).eq(Order::getOrderSn, orderSn).one();
        if (order == null || !Objects.equals(order.getStatus(), OrderStatus.NEW.getCode())) {
            return;
        }
        // TODO 查询支付平台是否已经支付
        lambdaUpdate().eq(Order::getOrderSn, orderSn).set(Order::getStatus, OrderStatus.CANCELED.getCode()).update();

        rabbitTemplate.convertAndSend(StockMqConstants.STOCK_EXCHANGE, StockMqConstants.UNLOCK_STOCK_ROUTING_KEY, orderSn);
    }

    @Override
    public String pay(String orderSn) {

        Order order = lambdaQuery().select(Order::getTotalAmount).eq(Order::getOrderSn, orderSn).one();
        BigDecimal price = order.getTotalAmount().setScale(2, RoundingMode.UP);
        try {
            return AlipayUtil.pay(orderSn, "Subject", price.toString());
        } catch (AlipayApiException e) {
            throw new MallServerException(ResponseEnum.USER_ERROR);
        }
    }

    @Override
    public PageVO<OrderListItemVO> page(Integer page, Integer size) {

        Long uid = ContextHolder.getUser().getUid();
        Page<Order> orderPage = PageUtil.newPage(Long.valueOf(page), Long.valueOf(size));
        lambdaQuery().eq(Order::getMemberId, uid).page(orderPage);
        List<String> orderSnList = orderPage.getRecords().stream().map(Order::getOrderSn).collect(Collectors.toList());
        List<OrderItem> orderItemList = orderItemService.getOrderItemByOrderSns(orderSnList);
        return PageUtil.pageVO(orderPage, order -> {
            OrderListItemVO orderListItemVO = new OrderListItemVO();
            BeanUtils.copyProperties(order, orderListItemVO);
            List<OrderItem> orderItems = orderItemList.stream().filter((item) -> Objects.equals(order.getOrderSn(), item.getOrderSn())).collect(Collectors.toList());
            orderListItemVO.setItems(orderItems);
            return orderListItemVO;
        });

    }

    @Override
    public void handlePayResult(PaySyncResultDTO paySyncResultDTO) {

        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOrderSn(paySyncResultDTO.getOut_trade_no());
        paymentInfo.setAlipayTradeNo(paySyncResultDTO.getTrade_no());
        paymentInfoService.save(paymentInfo);

        // 异步方式需要判断状态
//        if (!Objects.equals(payAsyncResultDTO.getTrade_status(), "TRADE_SUCCESS")
//                && !Objects.equals(payAsyncResultDTO.getTrade_status(), "TRADE_FINISHED")) {
//            throw new MallServerException(ResponseEnum.PAY_ERROR);
//        }
        lambdaUpdate().eq(Order::getOrderSn, paymentInfo.getOrderSn()).set(Order::getStatus, OrderStatus.PAID.getCode()).update();
        // 发送消息 扣减库存
        rabbitTemplate.convertAndSend(StockMqConstants.STOCK_EXCHANGE, StockMqConstants.DEDUCT_STOCK_ROUTING_KEY, paymentInfo.getOrderSn());
    }

    @Transactional
    @Override
    public void createSeckillOrder(SeckillOrderDTO seckillOrderDTO) {

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderSn(seckillOrderDTO.getOrderSn());
        orderItem.setSkuPrice(seckillOrderDTO.getPrice());
        orderItem.setRealAmount(seckillOrderDTO.getSeckillPrice().multiply(new BigDecimal(seckillOrderDTO.getCount())));
        orderItem.setSkuQuantity(seckillOrderDTO.getCount());
        orderItem.setCouponAmount(new BigDecimal(0));
        orderItem.setPromotionAmount(new BigDecimal(0));
        orderItem.setIntegrationAmount(new BigDecimal(0));

        Order order = new Order();
        order.setOrderSn(seckillOrderDTO.getOrderSn());
        order.setTotalAmount(orderItem.getRealAmount());
        order.setMemberId(seckillOrderDTO.getUserId());
        order.setModifyTime(LocalDateTime.now());
        order.setStatus(OrderStatus.NEW.getCode());

        orderItemService.save(orderItem);
        save(order);

        // 发送延迟消息 一分钟后关闭订单
        Message message = MessageBuilder.withBody(JSON.toJSONBytes(order.getOrderSn())).setHeader(MessageProperties.X_DELAY, OrderMqConstants.ORDER_CLOSE_DELAY_TIME).build();
        rabbitTemplate.convertAndSend(OrderMqConstants.ORDER_EXCHANGE, OrderMqConstants.ORDER_CLOSE_ROUTING_KEY, message);

    }


}

