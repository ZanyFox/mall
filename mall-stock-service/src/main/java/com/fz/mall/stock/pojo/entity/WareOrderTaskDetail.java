package com.fz.mall.stock.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
/**
 * <p>
 * 库存工作单
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@Data
@TableName("wms_ware_order_task_detail")
@ApiModel(value = "WareOrderTaskDetail对象", description = "库存工作单")
public class WareOrderTaskDetail implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("sku_id")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty("sku_name")
    @TableField("sku_name")
    private String skuName;

    @ApiModelProperty("购买个数")
    @TableField("sku_num")
    private Integer quantity;

    @ApiModelProperty("工作单id")
    @TableField("task_id")
    private Long taskId;

    @ApiModelProperty("仓库id")
    @TableField("ware_id")
    private Long wareId;

    @ApiModelProperty("1-已锁定  2-已解锁  3-扣减")
    @TableField("lock_status")
    private Integer lockStatus;
}
