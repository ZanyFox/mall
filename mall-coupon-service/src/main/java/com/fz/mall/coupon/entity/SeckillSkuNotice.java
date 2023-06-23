package com.fz.mall.coupon.entity;

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
 * 秒杀商品通知订阅
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@Data
@TableName("sms_seckill_sku_notice")
@ApiModel(value = "SeckillSkuNotice对象", description = "秒杀商品通知订阅")
public class SeckillSkuNotice implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("member_id")
    @TableField("member_id")
    private Long memberId;

    @ApiModelProperty("sku_id")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty("活动场次id")
    @TableField("session_id")
    private Long sessionId;

    @ApiModelProperty("订阅时间")
    @TableField("subcribe_time")
    private Timestamp subcribeTime;

    @ApiModelProperty("发送时间")
    @TableField("send_time")
    private Timestamp sendTime;

    @ApiModelProperty("通知方式[0-短信，1-邮件]")
    @TableField("notice_type")
    private Byte noticeType;
}
