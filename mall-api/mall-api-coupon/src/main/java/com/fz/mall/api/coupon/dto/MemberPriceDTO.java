/**
  * Copyright 2019 bejson.com 
  */
package com.fz.mall.api.coupon.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MemberPriceDTO {

    private Long id;
    private String name;
    private BigDecimal price;

}