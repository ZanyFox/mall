/**
 * Copyright 2019 bejson.com
 */
package com.fz.mall.goods.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SpuSaveVO {

    private String spuName;
    private String spuDescription;
    private Long catalogId;
    private Long brandId;
    private BigDecimal weight;
    private int publishStatus;
    private List<String> decript;
    private List<String> images;
    private BoundsVO bounds;
    private List<BaseAttrVO> baseAttrs;
    private List<SkusVO> skus;



}