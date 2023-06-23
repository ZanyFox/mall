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
 * 商品spu积分设置
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@Data
@TableName("sms_spu_bounds")
@ApiModel(value = "SpuBounds对象", description = "商品spu积分设置")
public class SpuBounds implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @TableField("spu_id")
    private Long spuId;

    @ApiModelProperty("成长积分")
    @TableField("grow_bounds")
    private BigDecimal growBounds;

    @ApiModelProperty("购物积分")
    @TableField("buy_bounds")
    private BigDecimal buyBounds;

    @ApiModelProperty("优惠生效情况[1111（四个状态位，从右到左）;0 - 无优惠，成长积分是否赠送;1 - 无优惠，购物积分是否赠送;2 - 有优惠，成长积分是否赠送;3 - 有优惠，购物积分是否赠送【状态位0：不赠送，1：赠送】]")
    @TableField("work")
    private Byte work;
}
