package com.fz.mall.api.dto;


import lombok.Data;

@Data
public class LockStockResultDTO {

    private Long skuId;

    private Integer quantity;

    /**
     * 是否锁库存成功
     */
    private Boolean status;
}
