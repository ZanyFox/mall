package com.fz.mall.api.goods.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderSpuInfoDTO {


    private Long id;

    private String spuName;

    private Long categoryId;

    private Long brandId;

    private BigDecimal weight;



}
