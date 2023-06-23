package com.fz.mall.coupon.entity;

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
 * 秒杀活动
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@Data
@TableName("sms_seckill_promotion")
@ApiModel(value = "SeckillPromotion对象", description = "秒杀活动")
public class SeckillPromotion implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("活动标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("开始日期")
    @TableField("start_time")
    private Timestamp startTime;

    @ApiModelProperty("结束日期")
    @TableField("end_time")
    private Timestamp endTime;

    @ApiModelProperty("上下线状态")
    @TableField("status")
    private Byte status;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Timestamp createTime;

    @ApiModelProperty("创建人")
    @TableField("user_id")
    private Long userId;
}
