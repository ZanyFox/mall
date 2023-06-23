package com.fz.mall.stock.pojo.entity;

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
 *
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@Data
@TableName("undo_log")
@ApiModel(value = "UndoLog对象", description = "")
public class UndoLog implements Serializable{

    @TableId(value = "id")
    private Long id;

    @TableField("branch_id")
    private Long branchId;

    @TableField("xid")
    private String xid;

    @TableField("context")
    private String context;

    @TableField("rollback_info")
    private byte[] rollbackInfo;

    @TableField("log_status")
    private Integer logStatus;

    @TableField("log_created")
    private Timestamp logCreated;

    @TableField("log_modified")
    private Timestamp logModified;

    @TableField("ext")
    private String ext;
}
