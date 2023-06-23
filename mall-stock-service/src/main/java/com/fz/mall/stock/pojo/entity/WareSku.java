package com.fz.mall.stock.pojo.entity;

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
 * 商品库存
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@Data
@TableName("wms_ware_sku")
@ApiModel(value = "WareSku对象", description = "商品库存")
public class WareSku implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("sku_id")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty("仓库id")
    @TableField("ware_id")
    private Long wareId;

    @ApiModelProperty("库存数")
    @TableField("stock")
    private Integer stock;

    @ApiModelProperty("sku_name")
    @TableField("sku_name")
    private String skuName;

    @ApiModelProperty("锁定库存")
    @TableField("stock_locked")
    private Integer stockLocked;
}
