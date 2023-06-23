package com.fz.mall.stock.constant;

import lombok.Getter;

@Getter
public enum PurchaseStatusEnum {

    CREATED(0, "新建"),
    ASSIGNED(1, "已分配"),
    RECEIVED(2, "已领取"),
    FINISHED(3, "已完成"),
    ERROR(4, "异常");

    private final String msg;
    private final Integer code;

    PurchaseStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
