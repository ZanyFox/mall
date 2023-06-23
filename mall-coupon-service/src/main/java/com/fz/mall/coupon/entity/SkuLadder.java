package com.fz.mall.coupon.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * <p>
 * 商品阶梯价格
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@Data
@TableName("sms_sku_ladder")
@ApiModel(value = "SkuLadder对象", description = "商品阶梯价格")
public class SkuLadder implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("spu_id")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty("满几件")
    @TableField("full_count")
    private Integer fullCount;

    @ApiModelProperty("打几折")
    @TableField("discount")
    private BigDecimal discount;

    @ApiModelProperty("折后价")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty("是否叠加其他优惠[0-不可叠加，1-可叠加]")
    @TableField("add_other")
    private Integer addOther;
}
