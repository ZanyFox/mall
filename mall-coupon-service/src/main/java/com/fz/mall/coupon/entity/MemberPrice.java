package com.fz.mall.coupon.entity;

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
 * 商品会员价格
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@Data
@TableName("sms_member_price")
@ApiModel(value = "MemberPrice对象", description = "商品会员价格")
public class MemberPrice implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("sku_id")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty("会员等级id")
    @TableField("member_level_id")
    private Long memberLevelId;

    @ApiModelProperty("会员等级名")
    @TableField("member_level_name")
    private String memberLevelName;

    @ApiModelProperty("会员对应价格")
    @TableField("member_price")
    private BigDecimal memberPrice;

    @ApiModelProperty("可否叠加其他优惠[0-不可叠加优惠，1-可叠加]")
    @TableField("add_other")
    private Integer addOther;
}
