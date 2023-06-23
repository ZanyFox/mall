package com.fz.mall.coupon.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.sql.Timestamp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
/**
 * <p>
 * 优惠券领取历史记录
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@Data
@TableName("sms_coupon_history")
@ApiModel(value = "CouponHistory对象", description = "优惠券领取历史记录")
public class CouponHistory implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("优惠券id")
    @TableField("coupon_id")
    private Long couponId;

    @ApiModelProperty("会员id")
    @TableField("member_id")
    private Long memberId;

    @ApiModelProperty("会员名字")
    @TableField("member_nick_name")
    private String memberNickName;

    @ApiModelProperty("获取方式[0->后台赠送；1->主动领取]")
    @TableField("get_type")
    private Byte getType;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Timestamp createTime;

    @ApiModelProperty("使用状态[0->未使用；1->已使用；2->已过期]")
    @TableField("use_type")
    private Byte useType;

    @ApiModelProperty("使用时间")
    @TableField("use_time")
    private Timestamp useTime;

    @ApiModelProperty("订单id")
    @TableField("order_id")
    private Long orderId;

    @ApiModelProperty("订单号")
    @TableField("order_sn")
    private Long orderSn;
}
