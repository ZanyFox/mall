package com.fz.mall.api.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FareDTO {

    private Long sourceAddrId;

    private Long destAddrId;

    private String sourceAddrName;

    private String destAddrName;

    private BigDecimal fare;
}
