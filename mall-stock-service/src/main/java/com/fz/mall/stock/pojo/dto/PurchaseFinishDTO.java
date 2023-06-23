package com.fz.mall.stock.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseFinishDTO {

    /**
     * 采购单ID
     */
    private Long purchaseId;

    /**
     * 采购项
     */
    private List<PurchaseItemFinishDTO> items;

}
