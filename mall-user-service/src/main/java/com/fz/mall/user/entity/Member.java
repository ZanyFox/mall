package com.fz.mall.user.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 会员
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
@TableName("ums_member")
@ApiModel(value = "Member对象", description = "会员")
public class Member implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("会员等级id")
    @TableField("level_id")
    private Long levelId;

    @ApiModelProperty("用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("昵称")
    @TableField("nickname")
    private String nickname;

    @ApiModelProperty("手机号码")
    @TableField("mobile")
    private String mobile;

    @ApiModelProperty("邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty("头像")
    @TableField("header")
    private String header;

    @ApiModelProperty("性别")
    @TableField("gender")
    private Byte gender;

    @ApiModelProperty("生日")
    @TableField("birth")
    private LocalDate birth;

    @ApiModelProperty("所在城市")
    @TableField("city")
    private String city;

    @ApiModelProperty("职业")
    @TableField("job")
    private String job;

    @ApiModelProperty("个性签名")
    @TableField("sign")
    private String sign;

    @ApiModelProperty("用户来源")
    @TableField("source_type")
    private Byte sourceType;

    @ApiModelProperty("积分")
    @TableField("integration")
    private Integer integration;

    @ApiModelProperty("成长值")
    @TableField("growth")
    private Integer growth;

    @ApiModelProperty("启用状态")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("用户头像")
    @TableField("avatar_url")
    private String avatarUrl;

    @ApiModelProperty("注册时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
