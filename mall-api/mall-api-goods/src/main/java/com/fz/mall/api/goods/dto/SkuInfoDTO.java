package com.fz.mall.api.goods.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SkuInfoDTO implements Serializable {

    private Long skuId;

    private Long spuId;

    private String skuName;

    private String skuDesc;

    @JsonProperty("catalogId")
    private Long categoryId;

    private Long brandId;

    private String skuDefaultImg;

    private String skuTitle;

    private String skuSubtitle;

    private BigDecimal price;

    private Long saleCount;
}
