package com.fz.mall.common.rabbitmq.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class SeckillOrderDTO implements Serializable {

    private String orderSn;

    private Long promotionSessionId;

    private Long skuId;

    private Integer count;

    private BigDecimal seckillPrice;

    private Long userId;

    private Long addrId;

    private BigDecimal price;

    private String skuDefaultImg;
}
