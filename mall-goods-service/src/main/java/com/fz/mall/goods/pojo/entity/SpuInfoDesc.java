package com.fz.mall.goods.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * spu信息介绍
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
@TableName("pms_spu_info_desc")
@ApiModel(value = "SpuInfoDesc对象", description = "spu信息介绍")
public class SpuInfoDesc implements Serializable{

    @ApiModelProperty("商品id")
    @TableId(value = "spu_id", type = IdType.AUTO)
    private Long spuId;

    @ApiModelProperty("商品介绍")
    @TableField("decript")
    @JsonProperty("decript")
    private String description;
}
