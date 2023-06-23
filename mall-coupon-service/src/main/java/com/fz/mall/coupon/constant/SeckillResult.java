package com.fz.mall.coupon.constant;

public enum SeckillResult {

    SUCCESS("购买成功", 0),
    STOCK_LACKING("库存不足", 1),
    BEYOND_LIMIT("超出购买限制", 2);

    private final long code;

    SeckillResult(String msg, long code) {
        this.code = code;
    }

    public long getCode() {
        return code;
    }
}
