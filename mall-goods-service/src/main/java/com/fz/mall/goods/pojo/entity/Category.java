package com.fz.mall.goods.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 商品三级分类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
@TableName("pms_category")
@ApiModel(value = "Category对象", description = "商品三级分类")
public class Category implements Serializable{

    @ApiModelProperty("分类id")
    @TableId(value = "cat_id")
    private Long catId;

    @ApiModelProperty("分类名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("父分类id")
    @TableField("parent_cid")
    private Long parentCid;

    @ApiModelProperty("层级")
    @TableField("cat_level")
    private Integer catLevel;

    @ApiModelProperty("是否显示[0-不显示，1显示]")
    @TableLogic(value = "1", delval = "0")
    @TableField("show_status")
    private Byte showStatus;

    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("图标地址")
    @TableField("icon")
    private String icon;

    @ApiModelProperty("计量单位")
    @TableField("product_unit")
    private String productUnit;

    @ApiModelProperty("商品数量")
    @TableField("product_count")
    private Integer productCount;

    @ApiModelProperty("子分类")
    @TableField(exist = false)
    // 当不为空时才返回该字段
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Category>  children;
}
