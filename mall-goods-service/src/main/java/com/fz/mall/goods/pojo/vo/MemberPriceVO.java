/**
  * Copyright 2019 bejson.com 
  */
package com.fz.mall.goods.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MemberPriceVO {

    private Long id;
    private String name;
    private BigDecimal price;

}