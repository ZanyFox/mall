package com.fz.mall.api.order.constant;

/**
 * 订单状态枚举
 *
 * @author Jerry
 */

public enum OrderStatus {
    NEW(0, "待付款"),
    PAID(1, "已付款"),
    SENT(2, "已发货"),
    RECEIVED(3, "已完成"),
    CANCELED(4, "已取消"),
    SERVICING(5, "售后中"),
    SERVICED(6, "售后完成");
    private final Integer code;
    private final String msg;

    OrderStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
