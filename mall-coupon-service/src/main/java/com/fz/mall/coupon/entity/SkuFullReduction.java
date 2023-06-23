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
 * 商品满减信息
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@Data
@TableName("sms_sku_full_reduction")
@ApiModel(value = "SkuFullReduction对象", description = "商品满减信息")
public class SkuFullReduction implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("spu_id")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty("满多少")
    @TableField("full_price")
    private BigDecimal fullPrice;

    @ApiModelProperty("减多少")
    @TableField("reduce_price")
    private BigDecimal reducePrice;

    @ApiModelProperty("是否参与其他优惠")
    @TableField("add_other")
    private Byte addOther;
}
