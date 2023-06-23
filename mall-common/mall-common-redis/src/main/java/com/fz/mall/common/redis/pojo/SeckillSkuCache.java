package com.fz.mall.common.redis.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SeckillSkuCache {


    private Long id;

    private Long promotionId;

    private Long promotionSessionId;

    private Long skuId;

    private BigDecimal seckillPrice;

    private Integer seckillCount;

    private Integer seckillLimit;

    private Integer seckillSort;

    private String skuTitle;

    private BigDecimal price;

    private String skuDefaultImg;

    /**
     * 秒杀随机码
     */
    private String code;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
