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
 * sku图片
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
@TableName("pms_sku_images")
@ApiModel(value = "SkuImages对象", description = "sku图片")
public class SkuImages implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("sku_id")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty("图片地址")
    @TableField("img_url")
    private String imgUrl;

    @ApiModelProperty("排序")
    @TableField("img_sort")
    private Integer imgSort;

    @ApiModelProperty("默认图[0 - 不是默认图，1 - 是默认图]")
    @TableField("default_img")
    private Integer defaultImg;
}
