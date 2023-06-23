package com.fz.mall.order.mq;

import com.alibaba.fastjson2.JSON;
import com.fz.mall.common.rabbitmq.constant.OrderMqConstants;
import com.fz.mall.common.rabbitmq.dto.SeckillOrderDTO;
import com.fz.mall.common.redis.pojo.CartCache;
import com.fz.mall.common.redis.constant.CartCacheConstants;
import com.fz.mall.order.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
public class OrderListener {


    private OrderService orderService;

    private StringRedisTemplate redisTemplate;

    /**
     * 关闭订单 并且发送解锁库存消息
     *
     * @param orderSn
     */
    @RabbitListener(queues = OrderMqConstants.ORDER_CLOSE_QUEUE)
    public void orderCloseListener(String orderSn) {
        log.info("收到关闭订单消息: {}", orderSn);
        orderService.closeOrder(orderSn);
    }

    /**
     * 订单创建成功 清空购物车
     *
     * @param uid
     */
    @RabbitListener(queues = OrderMqConstants.ORDER_CREATE_QUEUE)
    public void orderCreateListener(Long uid) {
        String key = CartCacheConstants.CART + uid;
        Map<Object, Object> cartCacheMap = redisTemplate.opsForHash().entries(key);

        Object[] skuIds = cartCacheMap.entrySet().stream()
                .map((entry) -> {
                    CartCache cartCache = JSON.parseObject(String.valueOf(entry.getValue()), CartCache.class);
                    cartCache.setSkuId(Long.valueOf(entry.getKey().toString()));
                    return cartCache;
                })
                .filter(CartCache::getChecked)
                .map((item) -> item.getSkuId().toString()).toArray();

        redisTemplate.opsForHash().delete(key, skuIds);
    }

    @RabbitListener(queues = OrderMqConstants.ORDER_SECKILL_CREATE_QUEUE)
    public void orderSeckillCreateListener(SeckillOrderDTO seckillOrderDTO) {
        log.info("接收到创建秒杀订单的消息: {}", seckillOrderDTO);
        orderService.createSeckillOrder(seckillOrderDTO);
    }
}
