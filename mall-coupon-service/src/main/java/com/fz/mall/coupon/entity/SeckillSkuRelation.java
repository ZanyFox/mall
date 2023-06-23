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
 * 秒杀活动商品关联
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@Data
@TableName("sms_seckill_sku_relation")
@ApiModel(value = "SeckillSkuRelation对象", description = "秒杀活动商品关联")
public class SeckillSkuRelation implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("活动id")
    @TableField("promotion_id")
    private Long promotionId;

    @ApiModelProperty("活动场次id")
    @TableField("promotion_session_id")
    private Long promotionSessionId;

    @ApiModelProperty("商品id")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty("秒杀价格")
    @TableField("seckill_price")
    private BigDecimal seckillPrice;

    @ApiModelProperty("秒杀总量")
    @TableField("seckill_count")
    private Integer seckillCount;

    @ApiModelProperty("每人限购数量")
    @TableField("seckill_limit")
    private Integer seckillLimit;

    @ApiModelProperty("排序")
    @TableField("seckill_sort")
    private Integer seckillSort;
}
