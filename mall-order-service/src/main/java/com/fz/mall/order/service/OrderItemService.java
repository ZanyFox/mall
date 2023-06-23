package com.fz.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fz.mall.order.entity.OrderItem;

import java.util.List;

/**
 * <p>
 * 订单项信息 服务类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
public interface OrderItemService extends IService<OrderItem> {

    List<OrderItem> getOrderItemByOrderSns(List<String> orderSns);
}
