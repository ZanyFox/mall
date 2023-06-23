package com.fz.mall.order.mapper;

import com.fz.mall.order.entity.OrderItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单项信息 Mapper 接口
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
public interface OrderItemMapper extends BaseMapper<OrderItem> {

    List<OrderItem> getOrderItemByOrderSns(@Param("orderSns") List<String> orderSns);
}
