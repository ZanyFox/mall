package com.fz.mall.common.redis.pojo;

import lombok.Data;

@Data
public class CartCache {

    private Long skuId;

    private Integer quantity;

    private Boolean checked;
}
