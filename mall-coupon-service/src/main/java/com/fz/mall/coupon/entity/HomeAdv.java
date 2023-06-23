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
 * 首页轮播广告
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@Data
@TableName("sms_home_adv")
@ApiModel(value = "HomeAdv对象", description = "首页轮播广告")
public class HomeAdv implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("名字")
    @TableField("name")
    private String name;

    @ApiModelProperty("图片地址")
    @TableField("pic")
    private String pic;

    @ApiModelProperty("开始时间")
    @TableField("start_time")
    private Timestamp startTime;

    @ApiModelProperty("结束时间")
    @TableField("end_time")
    private Timestamp endTime;

    @ApiModelProperty("状态")
    @TableField("status")
    private Byte status;

    @ApiModelProperty("点击数")
    @TableField("click_count")
    private Integer clickCount;

    @ApiModelProperty("广告详情连接地址")
    @TableField("url")
    private String url;

    @ApiModelProperty("备注")
    @TableField("note")
    private String note;

    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("发布者")
    @TableField("publisher_id")
    private Long publisherId;

    @ApiModelProperty("审核者")
    @TableField("auth_id")
    private Long authId;
}
