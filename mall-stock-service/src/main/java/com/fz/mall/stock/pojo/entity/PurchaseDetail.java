package com.fz.mall.stock.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
/**
 * <p>
 *
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@Data
@TableName("wms_purchase_detail")
@ApiModel(value = "PurchaseDetail对象", description = "")
public class PurchaseDetail implements Serializable{

    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("采购单id")
    @TableField("purchase_id")
    private Long purchaseId;

    @ApiModelProperty("采购商品id")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty("采购数量")
    @TableField("sku_num")
    private Integer skuNum;

    @ApiModelProperty("采购金额")
    @TableField("sku_price")
    private BigDecimal skuPrice;

    @ApiModelProperty("仓库id")
    @TableField("ware_id")
    private Long wareId;

    @ApiModelProperty("状态[0新建，1已分配，2正在采购，3已完成，4采购失败]")
    @TableField("status")
    private Integer status;
}
