package com.fz.mall.stock.constant;


import lombok.Getter;

@Getter
public enum PurchaseDetailStatusEnum {

    CREATED(0, "新建"),
    ASSIGNED(1, "已分配"),
    BUYING(2, "正在采购"),
    FINISHED(3, "已完成"),
    FAILURE(4, "采购失败");

    private String msg;
    private Integer code;

    PurchaseDetailStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
