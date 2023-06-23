package com.fz.mall.goods.constant;

public enum SpuStatusEnum {

    CREATED("新建"),
    SALE("上架"),
    NOT_SALE("下架");

    private final String status;

    public String getStatus() {
        return status;
    }

    SpuStatusEnum(String status) {
        this.status = status;
    }

}
