package com.fz.mall.coupon.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
/**
 * <p>
 * 优惠券分类关联
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@Data
@TableName("sms_coupon_spu_category_relation")
@ApiModel(value = "CouponSpuCategoryRelation对象", description = "优惠券分类关联")
public class CouponSpuCategoryRelation implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("优惠券id")
    @TableField("coupon_id")
    private Long couponId;

    @ApiModelProperty("产品分类id")
    @TableField("category_id")
    private Long categoryId;

    @ApiModelProperty("产品分类名称")
    @TableField("category_name")
    private String categoryName;
}
