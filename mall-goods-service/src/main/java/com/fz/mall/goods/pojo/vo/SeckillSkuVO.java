package com.fz.mall.goods.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class SeckillSkuVO {


    private Long promotionId;

    private Long promotionSessionId;

    private Long skuId;

    private BigDecimal seckillPrice;

    private Integer seckillCount;

    private Integer seckillLimit;

    private Integer seckillSort;

    private Long  startTime;

    private Long endTime;

    private String code;

}
