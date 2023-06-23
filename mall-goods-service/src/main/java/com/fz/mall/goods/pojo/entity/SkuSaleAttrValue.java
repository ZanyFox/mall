package com.fz.mall.goods.pojo.entity;

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
 * sku销售属性&值
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
@TableName("pms_sku_sale_attr_value")
@ApiModel(value = "SkuSaleAttrValue对象", description = "sku销售属性&值")
public class SkuSaleAttrValue implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("sku_id")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty("attr_id")
    @TableField("attr_id")
    private Long attrId;

    @ApiModelProperty("销售属性名")
    @TableField("attr_name")
    private String attrName;

    @ApiModelProperty("销售属性值")
    @TableField("attr_value")
    private String attrValue;

    @ApiModelProperty("顺序")
    @TableField("attr_sort")
    private Integer attrSort;

    @ApiModelProperty("0-普通销售属性 1-购买属性")
    @TableField("type")
    private Integer type;
}
