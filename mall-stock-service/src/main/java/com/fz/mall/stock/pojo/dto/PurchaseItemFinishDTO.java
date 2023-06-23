package com.fz.mall.stock.pojo.dto;

import lombok.Data;

/**
 * 采购完成每一项传输类
 */
@Data
public class PurchaseItemFinishDTO {

    /**
     * 采购项ID
     */
    private Long purchaseItemId;
    /**
     * 采购状态
     */
    private Integer status;
    /**
     * 采购信息
     */
    private String msg;
}
