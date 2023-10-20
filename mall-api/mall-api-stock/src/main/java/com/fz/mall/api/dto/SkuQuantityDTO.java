package com.fz.mall.api.dto;

import lombok.Data;

/**
 * 商品sku以及对应数量
 */
@Data
public class SkuQuantityDTO {

    private Long skuId;

    private Integer quantity;
}
