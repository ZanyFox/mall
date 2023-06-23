package com.fz.mall.order.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;



@Data
public class OrderItemInfoVO {

    private Long skuId;

    private Boolean check;

    private String title;

    private String image;

    private List<String> skuAttrValues;

    private BigDecimal price;

    private Integer count;

    private Boolean hasStock;

    private BigDecimal totalPrice;

    private BigDecimal weight = new BigDecimal(0);
}
