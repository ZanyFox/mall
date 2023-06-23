package com.fz.mall.stock.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.sql.Timestamp;
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
@TableName("wms_ware_order_task")
@ApiModel(value = "WareOrderTask对象", description = "库存工作单")
public class WareOrderTask implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("order_id")
    @TableField("order_id")
    private Long orderId;

    @ApiModelProperty("order_sn")
    @TableField("order_sn")
    private String orderSn;

    @ApiModelProperty("收货人")
    @TableField("consignee")
    private String consignee;

    @ApiModelProperty("收货人电话")
    @TableField("consignee_tel")
    private String consigneeTel;

    @ApiModelProperty("配送地址")
    @TableField("delivery_address")
    private String deliveryAddress;

    @ApiModelProperty("订单备注")
    @TableField("order_comment")
    private String orderComment;

    @ApiModelProperty("付款方式【 1:在线付款 2:货到付款】")
    @TableField("payment_way")
    private Byte paymentWay;

    @ApiModelProperty("任务状态")
    @TableField("task_status")
    private Byte taskStatus;

    @ApiModelProperty("订单描述")
    @TableField("order_body")
    private String orderBody;

    @ApiModelProperty("物流单号")
    @TableField("tracking_no")
    private String trackingNo;

    @ApiModelProperty("create_time")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Timestamp createTime;

    @ApiModelProperty("仓库id")
    @TableField("ware_id")
    private Long wareId;

    @ApiModelProperty("工作单备注")
    @TableField("task_comment")
    private String taskComment;
}
