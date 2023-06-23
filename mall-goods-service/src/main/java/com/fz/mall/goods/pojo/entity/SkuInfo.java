package com.fz.mall.goods.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
/**
 * <p>
 * sku信息
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
@TableName("pms_sku_info")
@ApiModel(value = "SkuInfo对象", description = "sku信息")
public class SkuInfo implements Serializable{

    @ApiModelProperty("skuId")
    @TableId(value = "sku_id")
    private Long skuId;

    @ApiModelProperty("spuId")
    @TableField("spu_id")
    private Long spuId;

    @ApiModelProperty("sku名称")
    @TableField("sku_name")
    private String skuName;

    @ApiModelProperty("sku介绍描述")
    @TableField("sku_desc")
    private String skuDesc;

    @ApiModelProperty("所属分类id")
    @TableField("catalog_id")
    @JsonProperty("catalogId")
    private Long categoryId;

    @ApiModelProperty("品牌id")
    @TableField("brand_id")
    private Long brandId;

    @ApiModelProperty("默认图片")
    @TableField("sku_default_img")
    private String skuDefaultImg;

    @ApiModelProperty("标题")
    @TableField("sku_title")
    private String skuTitle;

    @ApiModelProperty("副标题")
    @TableField("sku_subtitle")
    private String skuSubtitle;

    @ApiModelProperty("价格")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty("销量")
    @TableField("sale_count")
    private Long saleCount;
}
