package com.fz.mall.api.goods.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartSkuInfoDTO {

    private Long skuId;

    private String skuDefaultImg;

    private String skuTitle;

    private BigDecimal price;

    private List<String> attrs;
}
