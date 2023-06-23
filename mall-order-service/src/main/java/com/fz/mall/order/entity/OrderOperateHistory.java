package com.fz.mall.order.entity;

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
 * 订单操作历史记录
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
@TableName("oms_order_operate_history")
@ApiModel(value = "OrderOperateHistory对象", description = "订单操作历史记录")
public class OrderOperateHistory implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("订单id")
    @TableField("order_id")
    private Long orderId;

    @ApiModelProperty("操作人[用户；系统；后台管理员]")
    @TableField("operate_man")
    private String operateMan;

    @ApiModelProperty("操作时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Timestamp createTime;

    @ApiModelProperty("订单状态【0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单】")
    @TableField("order_status")
    private Byte orderStatus;

    @ApiModelProperty("备注")
    @TableField("note")
    private String note;
}
