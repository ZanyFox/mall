package com.fz.mall.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 会员收货地址
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
@TableName("ums_member_receive_address")
@ApiModel(value = "MemberReceiveAddress对象", description = "会员收货地址")
public class MemberReceiveAddress implements Serializable {

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("member_id")
    @TableField("member_id")
    private Long memberId;

    @ApiModelProperty("收货人姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty("电话")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("邮政编码")
    @TableField("post_code")
    private String postCode;

    @ApiModelProperty("省份/直辖市")
    @TableField("province")
    private String province;

    @ApiModelProperty("城市")
    @TableField("city")
    private String city;

    @ApiModelProperty("区")
    @TableField("region")
    private String region;

    @ApiModelProperty("详细地址(街道)")
    @TableField("detail_address")
    private String detailAddress;

    @ApiModelProperty("省市区代码")
    @TableField("areacode")
    private String areacode;

    @ApiModelProperty("是否默认")
    @TableField("default_status")
    private Integer defaultStatus;
}
