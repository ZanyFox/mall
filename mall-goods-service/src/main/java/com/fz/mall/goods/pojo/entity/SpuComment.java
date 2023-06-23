package com.fz.mall.goods.pojo.entity;

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
 * 商品评价
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
@TableName("pms_spu_comment")
@ApiModel(value = "SpuComment对象", description = "商品评价")
public class SpuComment implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("sku_id")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty("spu_id")
    @TableField("spu_id")
    private Long spuId;

    @ApiModelProperty("商品名字")
    @TableField("spu_name")
    private String spuName;

    @ApiModelProperty("会员昵称")
    @TableField("member_nick_name")
    private String memberNickName;

    @ApiModelProperty("星级")
    @TableField("star")
    private Byte star;

    @ApiModelProperty("会员ip")
    @TableField("member_ip")
    private String memberIp;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Timestamp createTime;

    @ApiModelProperty("显示状态[0-不显示，1-显示]")
    @TableField("show_status")
    private Byte showStatus;

    @ApiModelProperty("购买时属性组合")
    @TableField("spu_attributes")
    private String spuAttributes;

    @ApiModelProperty("点赞数")
    @TableField("likes_count")
    private Integer likesCount;

    @ApiModelProperty("回复数")
    @TableField("reply_count")
    private Integer replyCount;

    @ApiModelProperty("评论图片/视频[json数据；[{type:文件类型,url:资源路径}]]")
    @TableField("resources")
    private String resources;

    @ApiModelProperty("内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("用户头像")
    @TableField("member_icon")
    private String memberIcon;

    @ApiModelProperty("评论类型[0 - 对商品的直接评论，1 - 对评论的回复]")
    @TableField("comment_type")
    private Byte commentType;
}
