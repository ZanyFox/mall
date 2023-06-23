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
 * 订单
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
@TableName("oms_order")
@ApiModel(value = "Order对象", description = "订单")
public class Order implements Serializable {

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("member_id")
    @TableField("member_id")
    private Long memberId;

    @ApiModelProperty("订单号")
    @TableField("order_sn")
    private String orderSn;

    @ApiModelProperty("使用的优惠券")
    @TableField("coupon_id")
    private Long couponId;

    @ApiModelProperty("create_time")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty("用户名")
    @TableField("member_username")
    private String memberUsername;

    @ApiModelProperty("订单总额")
    @TableField("total_amount")
    private BigDecimal totalAmount;

    @ApiModelProperty("应付总额")
    @TableField("pay_amount")
    private BigDecimal payAmount;

    @ApiModelProperty("运费金额")
    @TableField("freight_amount")
    private BigDecimal freightAmount;

    @ApiModelProperty("促销优化金额（促销价、满减、阶梯价）")
    @TableField("promotion_amount")
    private BigDecimal promotionAmount;

    @ApiModelProperty("积分抵扣金额")
    @TableField("integration_amount")
    private BigDecimal integrationAmount;

    @ApiModelProperty("优惠券抵扣金额")
    @TableField("coupon_amount")
    private BigDecimal couponAmount;

    @ApiModelProperty("后台调整订单使用的折扣金额")
    @TableField("discount_amount")
    private BigDecimal discountAmount;

    @ApiModelProperty("支付方式【1->支付宝；2->微信；3->银联； 4->货到付款；】")
    @TableField("pay_type")
    private Integer payType;

    @ApiModelProperty("订单来源[0->PC订单；1->app订单]")
    @TableField("source_type")
    private Integer sourceType;

    @ApiModelProperty("订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("物流公司(配送方式)")
    @TableField("delivery_company")
    private String deliveryCompany;

    @ApiModelProperty("物流单号")
    @TableField("delivery_sn")
    private String deliverySn;

    @ApiModelProperty("自动确认时间（天）")
    @TableField("auto_confirm_day")
    private Integer autoConfirmDay;

    @ApiModelProperty("可以获得的积分")
    @TableField("integration")
    private Integer integration;

    @ApiModelProperty("可以获得的成长值")
    @TableField("growth")
    private Integer growth;

    @ApiModelProperty("发票类型[0->不开发票；1->电子发票；2->纸质发票]")
    @TableField("bill_type")
    private Integer billType;

    @ApiModelProperty("发票抬头")
    @TableField("bill_header")
    private String billHeader;

    @ApiModelProperty("发票内容")
    @TableField("bill_content")
    private String billContent;

    @ApiModelProperty("收票人电话")
    @TableField("bill_receiver_phone")
    private String billReceiverPhone;

    @ApiModelProperty("收票人邮箱")
    @TableField("bill_receiver_email")
    private String billReceiverEmail;

    @ApiModelProperty("收货人姓名")
    @TableField("receiver_name")
    private String receiverName;

    @ApiModelProperty("收货人电话")
    @TableField("receiver_phone")
    private String receiverPhone;

    @ApiModelProperty("收货人邮编")
    @TableField("receiver_post_code")
    private String receiverPostCode;

    @ApiModelProperty("省份/直辖市")
    @TableField("receiver_province")
    private String receiverProvince;

    @ApiModelProperty("城市")
    @TableField("receiver_city")
    private String receiverCity;

    @ApiModelProperty("区")
    @TableField("receiver_region")
    private String receiverRegion;

    @ApiModelProperty("详细地址")
    @TableField("receiver_detail_address")
    private String receiverDetailAddress;

    @ApiModelProperty("订单备注")
    @TableField("note")
    private String note;

    @ApiModelProperty("确认收货状态[0->未确认；1->已确认]")
    @TableField("confirm_status")
    private Integer confirmStatus;

    @ApiModelProperty("删除状态【0->未删除；1->已删除】")
    @TableField("delete_status")
    private Integer deleteStatus;

    @ApiModelProperty("下单时使用的积分")
    @TableField("use_integration")
    private Integer useIntegration;

    @ApiModelProperty("支付时间")
    @TableField("payment_time")
    private LocalDateTime paymentTime;

    @ApiModelProperty("发货时间")
    @TableField("delivery_time")
    private LocalDateTime deliveryTime;

    @ApiModelProperty("确认收货时间")
    @TableField("receive_time")
    private LocalDateTime receiveTime;

    @ApiModelProperty("评价时间")
    @TableField("comment_time")
    private LocalDateTime commentTime;

    @ApiModelProperty("修改时间")
    @TableField("modify_time")
    private LocalDateTime modifyTime;
}
