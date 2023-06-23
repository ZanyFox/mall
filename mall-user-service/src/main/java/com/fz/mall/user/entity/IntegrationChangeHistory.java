package com.fz.mall.user.entity;

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
 * 积分变化历史记录
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
@TableName("ums_integration_change_history")
@ApiModel(value = "IntegrationChangeHistory对象", description = "积分变化历史记录")
public class IntegrationChangeHistory implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("member_id")
    @TableField("member_id")
    private Long memberId;

    @ApiModelProperty("create_time")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Timestamp createTime;

    @ApiModelProperty("变化的值")
    @TableField("change_count")
    private Integer changeCount;

    @ApiModelProperty("备注")
    @TableField("note")
    private String note;

    @ApiModelProperty("来源[0->购物；1->管理员修改;2->活动]")
    @TableField("source_tyoe")
    private Byte sourceTyoe;
}
