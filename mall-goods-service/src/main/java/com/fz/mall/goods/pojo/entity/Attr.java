package com.fz.mall.goods.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
/**
 * <p>
 * 商品属性
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
@TableName("pms_attr")
@ApiModel(value = "Attr对象", description = "商品属性")
public class Attr implements Serializable{

    @ApiModelProperty("属性id")
    @TableId(value = "attr_id")
    private Long attrId;

    @ApiModelProperty("属性名")
    @TableField("attr_name")
    private String attrName;

    @ApiModelProperty("是否需要检索[0-不需要，1-需要]")
    @TableField("search_type")
    private Integer searchType;

    @ApiModelProperty("属性图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty("可选值列表[用逗号分隔]")
    @TableField("value_select")
    private String valueSelect;

    @ApiModelProperty("属性类型[0-销售属性，1-基本属性，2-既是销售属性又是基本属性]")
    @TableField("attr_type")
    private Integer attrType;

    @ApiModelProperty("启用状态[0 - 禁用，1 - 启用]")
    @TableField("enable")
    private Long enable;

    @ApiModelProperty("所属分类")
    @TableField("catelog_id")
    private Long categoryId;

    @ApiModelProperty("快速展示【是否展示在介绍上；0-否 1-是】，在sku中仍然可以调整")
    @TableField("show_desc")
    private Integer showDesc;

}
