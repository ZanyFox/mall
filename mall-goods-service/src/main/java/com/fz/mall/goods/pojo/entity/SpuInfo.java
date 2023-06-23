package com.fz.mall.goods.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.sql.Timestamp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * spu信息
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
@TableName("pms_spu_info")
@ApiModel(value = "SpuInfo对象", description = "spu信息")
public class SpuInfo implements Serializable{

    @ApiModelProperty("商品id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("商品名称")
    @TableField("spu_name")
    private String spuName;

    @ApiModelProperty("商品描述")
    @TableField("spu_description")
    private String spuDescription;

    @ApiModelProperty("所属分类id")
    @TableField("catalog_id")
    private Long categoryId;

    @ApiModelProperty("品牌id")
    @TableField("brand_id")
    private Long brandId;

    @TableField("weight")
    private BigDecimal weight;

    @ApiModelProperty("上架状态[0 - 下架，1 - 上架]")
    @TableField("publish_status")
    private Integer publishStatus;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
