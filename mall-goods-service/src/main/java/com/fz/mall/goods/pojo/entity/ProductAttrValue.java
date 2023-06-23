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
 * spu属性值
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
@TableName("pms_product_attr_value")
@ApiModel(value = "ProductAttrValue对象", description = "spu属性值")
public class ProductAttrValue implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("商品id")
    @TableField("spu_id")
    private Long spuId;

    @ApiModelProperty("属性id")
    @TableField("attr_id")
    private Long attrId;

    @ApiModelProperty("属性名")
    @TableField("attr_name")
    private String attrName;

    @ApiModelProperty("属性值")
    @TableField("attr_value")
    private String attrValue;

    @ApiModelProperty("顺序")
    @TableField("attr_sort")
    private Integer attrSort;

    @ApiModelProperty("快速展示【是否展示在介绍上；0-否 1-是】")
    @TableField("quick_show")
    private Integer quickShow;

    @ApiModelProperty("是否可以被搜索【；0-否 1-是】")
    @TableField("search_type")
    private Integer searchType;
}
