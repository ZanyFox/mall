package com.fz.mall.order.pojo.vo;

import com.fz.mall.order.entity.OrderItem;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderListItemVO {

    private String orderSn;

    private String receiverName;

    private BigDecimal payAmount;

    private Integer status;

    private LocalDateTime createTime;

    private List<OrderItem> items;

}
