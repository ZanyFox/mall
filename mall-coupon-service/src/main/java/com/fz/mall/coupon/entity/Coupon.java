package com.fz.mall.coupon.entity;

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
 * 优惠券信息
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@Data
@TableName("sms_coupon")
@ApiModel(value = "Coupon对象", description = "优惠券信息")
public class Coupon implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("优惠卷类型[0->全场赠券；1->会员赠券；2->购物赠券；3->注册赠券]")
    @TableField("coupon_type")
    private Byte couponType;

    @ApiModelProperty("优惠券图片")
    @TableField("coupon_img")
    private String couponImg;

    @ApiModelProperty("优惠卷名字")
    @TableField("coupon_name")
    private String couponName;

    @ApiModelProperty("数量")
    @TableField("num")
    private Integer num;

    @ApiModelProperty("金额")
    @TableField("amount")
    private BigDecimal amount;

    @ApiModelProperty("每人限领张数")
    @TableField("per_limit")
    private Integer perLimit;

    @ApiModelProperty("使用门槛")
    @TableField("min_point")
    private BigDecimal minPoint;

    @ApiModelProperty("开始时间")
    @TableField("start_time")
    private Timestamp startTime;

    @ApiModelProperty("结束时间")
    @TableField("end_time")
    private Timestamp endTime;

    @ApiModelProperty("使用类型[0->全场通用；1->指定分类；2->指定商品]")
    @TableField("use_type")
    private Byte useType;

    @ApiModelProperty("备注")
    @TableField("note")
    private String note;

    @ApiModelProperty("发行数量")
    @TableField("publish_count")
    private Integer publishCount;

    @ApiModelProperty("已使用数量")
    @TableField("use_count")
    private Integer useCount;

    @ApiModelProperty("领取数量")
    @TableField("receive_count")
    private Integer receiveCount;

    @ApiModelProperty("可以领取的开始日期")
    @TableField("enable_start_time")
    private Timestamp enableStartTime;

    @ApiModelProperty("可以领取的结束日期")
    @TableField("enable_end_time")
    private Timestamp enableEndTime;

    @ApiModelProperty("优惠码")
    @TableField("code")
    private String code;

    @ApiModelProperty("可以领取的会员等级[0->不限等级，其他-对应等级]")
    @TableField("member_level")
    private Byte memberLevel;

    @ApiModelProperty("发布状态[0-未发布，1-已发布]")
    @TableField("publish")
    private Byte publish;
}
