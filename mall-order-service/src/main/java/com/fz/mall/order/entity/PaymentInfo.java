package com.fz.mall.order.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 支付信息表
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
@TableName("oms_payment_info")
@ApiModel(value = "PaymentInfo对象", description = "支付信息表")
public class PaymentInfo implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("订单号（对外业务号）")
    @TableField("order_sn")
    private String orderSn;

    @ApiModelProperty("订单id")
    @TableField("order_id")
    private Long orderId;

    @ApiModelProperty("支付宝交易流水号")
    @TableField("alipay_trade_no")
    private String alipayTradeNo;

    @ApiModelProperty("支付总金额")
    @TableField("total_amount")
    private BigDecimal totalAmount;

    @ApiModelProperty("交易内容")
    @TableField("subject")
    private String subject;

    @ApiModelProperty("支付状态")
    @TableField("payment_status")
    private String paymentStatus;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("确认时间")
    @TableField("confirm_time")
    private LocalDateTime confirmTime;

    @ApiModelProperty("回调内容")
    @TableField("callback_content")
    private String callbackContent;

    @ApiModelProperty("回调时间")
    @TableField("callback_time")
    private LocalDateTime callbackTime;
}
