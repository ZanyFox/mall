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
 * 品牌
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
@TableName("pms_brand")
@ApiModel(value = "Brand对象", description = "品牌")
public class Brand implements Serializable{

    @ApiModelProperty("品牌id")
    @TableId(value = "brand_id")
    private Long brandId;

    @ApiModelProperty("品牌名")
    @TableField("name")
    private String name;

    @ApiModelProperty("品牌logo地址")
    @TableField("logo")
    private String logo;

    @TableField("brand_search_img")
    private String brandSearchImg;

    @ApiModelProperty("介绍")
    @TableField("descript")
    private String descript;

    @ApiModelProperty("显示状态[0-不显示；1-显示]")
    @TableField("show_status")
    private Byte showStatus;

    @ApiModelProperty("检索首字母")
    @TableField("first_letter")
    private String firstLetter;

    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;
}
