package com.fz.mall.order.entity;

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
 * 订单项信息
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
@TableName("oms_order_item")
@ApiModel(value = "OrderItem对象", description = "订单项信息")
public class OrderItem implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("order_id")
    @TableField("order_id")
    private Long orderId;

    @ApiModelProperty("order_sn")
    @TableField("order_sn")
    private String orderSn;

    @ApiModelProperty("spu_id")
    @TableField("spu_id")
    private Long spuId;

    @ApiModelProperty("spu_name")
    @TableField("spu_name")
    private String spuName;

    @ApiModelProperty("spu_pic")
    @TableField("spu_pic")
    private String spuPic;

    @ApiModelProperty("品牌")
    @TableField("spu_brand")
    private String spuBrand;

    @ApiModelProperty("商品分类id")
    @TableField("category_id")
    private Long categoryId;

    @ApiModelProperty("商品sku编号")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty("商品sku名字")
    @TableField("sku_name")
    private String skuName;

    @ApiModelProperty("商品sku图片")
    @TableField("sku_pic")
    private String skuPic;

    @ApiModelProperty("商品sku价格")
    @TableField("sku_price")
    private BigDecimal skuPrice;

    @ApiModelProperty("商品购买的数量")
    @TableField("sku_quantity")
    private Integer skuQuantity;

    @ApiModelProperty("商品销售属性组合（JSON）")
    @TableField("sku_attrs_vals")
    private String skuAttrsVals;

    @ApiModelProperty("商品促销分解金额")
    @TableField("promotion_amount")
    private BigDecimal promotionAmount;

    @ApiModelProperty("优惠券优惠分解金额")
    @TableField("coupon_amount")
    private BigDecimal couponAmount;

    @ApiModelProperty("积分优惠分解金额")
    @TableField("integration_amount")
    private BigDecimal integrationAmount;

    @ApiModelProperty("该商品经过优惠后的分解金额")
    @TableField("real_amount")
    private BigDecimal realAmount;

    @ApiModelProperty("赠送积分")
    @TableField("gift_integration")
    private Integer giftIntegration;

    @ApiModelProperty("赠送成长值")
    @TableField("gift_growth")
    private Integer giftGrowth;
}
