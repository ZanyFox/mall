package com.fz.mall.stock.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.sql.Timestamp;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
/**
 * <p>
 * 采购信息
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@Data
@TableName("wms_purchase")
@ApiModel(value = "Purchase对象", description = "采购信息")
public class Purchase implements Serializable{

    @TableId(value = "id")
    private Long id;

    @TableField("assignee_id")
    private Long assigneeId;

    @TableField("assignee_name")
    private String assigneeName;

    @TableField("phone")
    private String phone;

    @TableField("priority")
    private Integer priority;

    @TableField("status")
    private Integer status;

    @TableField("ware_id")
    private Long wareId;

    @TableField("amount")
    private BigDecimal amount;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Timestamp createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Timestamp updateTime;
}
