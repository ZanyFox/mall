package com.fz.mall.goods.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
/**
 * <p>
 * 品牌分类关联
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
@TableName("pms_category_brand_relation")
@ApiModel(value = "CategoryBrandRelation对象", description = "品牌分类关联")
public class CategoryBrandRelation implements Serializable{

    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("品牌id")
    @TableField("brand_id")
    private Long brandId;

    @ApiModelProperty("分类id")
    @TableField("catelog_id")
    @JsonProperty("catelogId")
    private Long categoryId;

    @TableField("brand_name")
    private String brandName;

    @TableField("catelog_name")
    @JsonProperty("catelogName")
    private String categoryName;
}
