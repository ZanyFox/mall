package com.fz.mall.api.coupon.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SpuBoundDTO implements Serializable {

    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
