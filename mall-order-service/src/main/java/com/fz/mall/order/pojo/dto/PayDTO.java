package com.fz.mall.order.pojo.dto;

import lombok.Data;

@Data
public class PayDTO {

    private String out_trade_no;
    private String subject;
    private String total_amount;
    private String product_code = "FAST_INSTANT_TRADE_PAY";
    private String body;
}
