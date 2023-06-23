package com.fz.mall.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class LockStockDTO {

    private String orderSn;


    private List<SkuQuantityDTO> skuQuantityDTOS;

}
