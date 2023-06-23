package com.fz.mall.order.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.sql.Timestamp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
/**
 * <p>
 * 订单退货申请
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
@TableName("oms_order_return_apply")
@ApiModel(value = "OrderReturnApply对象", description = "订单退货申请")
public class OrderReturnApply implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("order_id")
    @TableField("order_id")
    private Long orderId;

    @ApiModelProperty("退货商品id")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty("订单编号")
    @TableField("order_sn")
    private String orderSn;

    @ApiModelProperty("申请时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Timestamp createTime;

    @ApiModelProperty("会员用户名")
    @TableField("member_username")
    private String memberUsername;

    @ApiModelProperty("退款金额")
    @TableField("return_amount")
    private BigDecimal returnAmount;

    @ApiModelProperty("退货人姓名")
    @TableField("return_name")
    private String returnName;

    @ApiModelProperty("退货人电话")
    @TableField("return_phone")
    private String returnPhone;

    @ApiModelProperty("申请状态[0->待处理；1->退货中；2->已完成；3->已拒绝]")
    @TableField("status")
    private Byte status;

    @ApiModelProperty("处理时间")
    @TableField("handle_time")
    private Timestamp handleTime;

    @ApiModelProperty("商品图片")
    @TableField("sku_img")
    private String skuImg;

    @ApiModelProperty("商品名称")
    @TableField("sku_name")
    private String skuName;

    @ApiModelProperty("商品品牌")
    @TableField("sku_brand")
    private String skuBrand;

    @ApiModelProperty("商品销售属性(JSON)")
    @TableField("sku_attrs_vals")
    private String skuAttrsVals;

    @ApiModelProperty("退货数量")
    @TableField("sku_count")
    private Integer skuCount;

    @ApiModelProperty("商品单价")
    @TableField("sku_price")
    private BigDecimal skuPrice;

    @ApiModelProperty("商品实际支付单价")
    @TableField("sku_real_price")
    private BigDecimal skuRealPrice;

    @ApiModelProperty("原因")
    @TableField("reason")
    private String reason;

    @ApiModelProperty("描述")
    @TableField("description述")
    private String description述;

    @ApiModelProperty("凭证图片，以逗号隔开")
    @TableField("desc_pics")
    private String descPics;

    @ApiModelProperty("处理备注")
    @TableField("handle_note")
    private String handleNote;

    @ApiModelProperty("处理人员")
    @TableField("handle_man")
    private String handleMan;

    @ApiModelProperty("收货人")
    @TableField("receive_man")
    private String receiveMan;

    @ApiModelProperty("收货时间")
    @TableField("receive_time")
    private Timestamp receiveTime;

    @ApiModelProperty("收货备注")
    @TableField("receive_note")
    private String receiveNote;

    @ApiModelProperty("收货电话")
    @TableField("receive_phone")
    private String receivePhone;

    @ApiModelProperty("公司收货地址")
    @TableField("company_address")
    private String companyAddress;
}
