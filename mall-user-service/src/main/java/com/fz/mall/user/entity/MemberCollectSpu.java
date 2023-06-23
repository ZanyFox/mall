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
 * 会员收藏的商品
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
@TableName("ums_member_collect_spu")
@ApiModel(value = "MemberCollectSpu对象", description = "会员收藏的商品")
public class MemberCollectSpu implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("会员id")
    @TableField("member_id")
    private Long memberId;

    @ApiModelProperty("spu_id")
    @TableField("spu_id")
    private Long spuId;

    @ApiModelProperty("spu_name")
    @TableField("spu_name")
    private String spuName;

    @ApiModelProperty("spu_img")
    @TableField("spu_img")
    private String spuImg;

    @ApiModelProperty("create_time")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Timestamp createTime;
}
