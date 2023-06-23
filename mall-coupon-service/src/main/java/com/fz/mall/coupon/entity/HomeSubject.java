package com.fz.mall.coupon.entity;

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
 * 首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@Data
@TableName("sms_home_subject")
@ApiModel(value = "HomeSubject对象", description = "首页专题表【jd首页下面很多专题，每个专题链接新的页面，展示专题商品信息】")
public class HomeSubject implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("专题名字")
    @TableField("name")
    private String name;

    @ApiModelProperty("专题标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("专题副标题")
    @TableField("sub_title")
    private String subTitle;

    @ApiModelProperty("显示状态")
    @TableField("status")
    private Byte status;

    @ApiModelProperty("详情连接")
    @TableField("url")
    private String url;

    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("专题图片地址")
    @TableField("img")
    private String img;
}
