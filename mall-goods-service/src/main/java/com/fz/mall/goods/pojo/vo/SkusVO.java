/**
  * Copyright 2019 bejson.com 
  */
package com.fz.mall.goods.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
public class SkusVO {

    private List<SimpleAttrVO> attr;
    private String skuName;
    private BigDecimal price;
    private String skuTitle;
    private String skuSubtitle;
    private List<ImagesVO> images;
    private List<String> descar;
    private int fullCount;
    private BigDecimal discount;
    private int countStatus;
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private int priceStatus;
    private List<MemberPriceVO> memberPrice;


}