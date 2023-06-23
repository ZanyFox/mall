package com.fz.mall.order.pojo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaySyncResultDTO {

    private LocalDateTime timestamp;

    private String out_trade_no;

    private String trade_no;
}

