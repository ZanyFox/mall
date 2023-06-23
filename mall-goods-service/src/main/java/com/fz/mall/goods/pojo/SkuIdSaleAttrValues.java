package com.fz.mall.goods.pojo;

import lombok.Data;

import java.util.List;

@Data
public class SkuIdSaleAttrValues {

    private Long skuId;

    private List<String> attrs;
}
