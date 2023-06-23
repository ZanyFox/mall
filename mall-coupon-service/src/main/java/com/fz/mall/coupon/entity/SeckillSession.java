package com.fz.mall.coupon.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 秒杀活动场次
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@Data
@TableName("sms_seckill_session")
@ApiModel(value = "SeckillSession对象", description = "秒杀活动场次")
public class SeckillSession implements Serializable {

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("场次名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("每日开始时间")
    @TableField("start_time")
    private LocalDateTime startTime;

    @ApiModelProperty("每日结束时间")
    @TableField("end_time")
    private LocalDateTime endTime;

    @ApiModelProperty("启用状态")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
