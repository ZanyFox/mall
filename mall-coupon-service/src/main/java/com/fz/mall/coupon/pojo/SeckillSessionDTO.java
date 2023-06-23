package com.fz.mall.coupon.pojo;

import com.fz.mall.coupon.entity.SeckillSkuRelation;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SeckillSessionDTO {


    private Long id;

    private String name;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer status;

    private List<SeckillSkuRelation> relations;
}
