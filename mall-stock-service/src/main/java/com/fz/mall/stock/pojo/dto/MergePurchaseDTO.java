package com.fz.mall.stock.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class MergePurchaseDTO {

    private Long purchaseId;
    private List<Long> items;
}
