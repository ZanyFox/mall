package com.fz.mall.order.pojo.param;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class OrderSubmitParam {


    /**
     * 收货地址
     */
    private Long addrId;


    /**
     * 支付方式
     */
    private Integer payType;


    /**
     * 防重提交
     */
    private String orderToken;


    /**
     * 前端提交金额 价格变动时可提示用户
     */
    private BigDecimal payPrice;


    private String remarks;

    private boolean seckill;

    private Long seckillSkuId;

    private Integer seckillQuantity;

    private Long seckillSessionId;

    private String seckillCode;
}
