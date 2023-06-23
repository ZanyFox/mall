package com.fz.mall.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
/**
 * <p>
 * 退款信息
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
@TableName("oms_refund_info")
@ApiModel(value = "RefundInfo对象", description = "退款信息")
public class RefundInfo implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("退款的订单")
    @TableField("order_return_id")
    private Long orderReturnId;

    @ApiModelProperty("退款金额")
    @TableField("refund")
    private BigDecimal refund;

    @ApiModelProperty("退款交易流水号")
    @TableField("refund_sn")
    private String refundSn;

    @ApiModelProperty("退款状态")
    @TableField("refund_status")
    private Byte refundStatus;

    @ApiModelProperty("退款渠道[1-支付宝，2-微信，3-银联，4-汇款]")
    @TableField("refund_channel")
    private Byte refundChannel;

    @TableField("refund_content")
    private String refundContent;
}
