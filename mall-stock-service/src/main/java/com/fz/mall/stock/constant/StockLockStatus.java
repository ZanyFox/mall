package com.fz.mall.stock.constant;

public enum StockLockStatus {

    LOCKED("已锁定", 0),
    UNLOCKED("已解锁", 1),
    DEDUCTED("已扣减", 2);

    private final String status;

    private final Integer code;

    public String getStatus() {
        return status;
    }

    public Integer getCode() {
        return code;
    }

    StockLockStatus(String status, Integer code) {
        this.status = status;
        this.code = code;
    }
}
