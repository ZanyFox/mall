package com.fz.mall.order.service.impl;

import com.fz.mall.order.entity.OrderItem;
import com.fz.mall.order.mapper.OrderItemMapper;
import com.fz.mall.order.service.OrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {


    private final OrderItemMapper orderItemMapper;

    @Override
    public List<OrderItem> getOrderItemByOrderSns(List<String> orderSns) {

        if (ObjectUtils.isEmpty(orderSns)) return null;
        return orderItemMapper.getOrderItemByOrderSns(orderSns);
    }
}
