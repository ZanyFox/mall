package com.fz.mall.stock.pojo.dto;

import lombok.Data;

@Data
public class SkuHasStockDTO {

    private Long skuId;
    private Boolean hasStock;
}
