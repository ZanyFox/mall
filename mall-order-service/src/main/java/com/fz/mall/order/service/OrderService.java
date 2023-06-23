package com.fz.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.common.rabbitmq.dto.SeckillOrderDTO;
import com.fz.mall.order.entity.Order;
import com.fz.mall.order.pojo.dto.PaySyncResultDTO;
import com.fz.mall.order.pojo.param.OrderSubmitParam;
import com.fz.mall.order.pojo.vo.OrderInfoVO;
import com.fz.mall.order.pojo.vo.OrderListItemVO;
import com.fz.mall.order.pojo.vo.OrderSubmitResponseVO;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
public interface OrderService extends IService<Order> {

    OrderInfoVO getOrderInfo();

    /**
     * 提交购物车订单 创建订单 验证令牌  锁库存
     *
     * @param orderSubmitParam
     */
    OrderSubmitResponseVO saveOrder(OrderSubmitParam orderSubmitParam);

    /**
     * 提交秒杀订单
     * @param orderSubmitParam
     * @return
     */
    OrderSubmitResponseVO submitSeckillOrder(OrderSubmitParam orderSubmitParam);

    /**
     * 关闭订单
     *
     * @param orderSn
     */
    void closeOrder(String orderSn);

    /**
     * 支付
     *
     * @param orderSn
     * @return
     */
    String pay(String orderSn);


    PageVO<OrderListItemVO> page(Integer page, Integer size);

    /**
     * 同步付款成功处理
     *
     * @param paySyncResultDTO
     */
    void handlePayResult(PaySyncResultDTO paySyncResultDTO);

    void createSeckillOrder(SeckillOrderDTO seckillOrderDTO);
}
