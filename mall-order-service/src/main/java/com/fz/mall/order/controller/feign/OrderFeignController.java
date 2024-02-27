package com.fz.mall.order.controller.feign;


import com.fz.mall.api.order.feign.OrderFeignClient;
import com.fz.mall.common.resp.ServRespEntity;
import com.fz.mall.order.entity.Order;
import com.fz.mall.order.service.OrderService;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class OrderFeignController implements OrderFeignClient {

    private OrderService orderService;

    @Override
    public ServRespEntity<Integer> getOrderStatusById(String orderSn) {

        Optional<Order> orderOptional = Optional.ofNullable(orderService.lambdaQuery().select(Order::getStatus).eq(Order::getOrderSn, orderSn).one());
        return ServRespEntity.success(orderOptional.orElse(new Order()).getStatus());
    }
}
