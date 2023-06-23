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
 * 会员收藏的专题活动
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
@TableName("ums_member_collect_subject")
@ApiModel(value = "MemberCollectSubject对象", description = "会员收藏的专题活动")
public class MemberCollectSubject implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("subject_id")
    @TableField("subject_id")
    private Long subjectId;

    @ApiModelProperty("subject_name")
    @TableField("subject_name")
    private String subjectName;

    @ApiModelProperty("subject_img")
    @TableField("subject_img")
    private String subjectImg;

    @ApiModelProperty("活动url")
    @TableField("subject_urll")
    private String subjectUrll;
}
