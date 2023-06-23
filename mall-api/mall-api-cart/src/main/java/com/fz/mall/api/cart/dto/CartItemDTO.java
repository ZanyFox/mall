package com.fz.mall.api.cart.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartItemDTO {

    private Long skuId;

    private Boolean check = true;

    private String title;

    private String image;

    private List<String> skuAttrValues;

    private BigDecimal price;

    private Integer count;

    private Boolean hasStock;

    private BigDecimal totalPrice;
}
