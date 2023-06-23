package com.fz.mall.goods.pojo.entity;

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
 * 商品评价回复关系
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
@TableName("pms_comment_replay")
@ApiModel(value = "CommentReplay对象", description = "商品评价回复关系")
public class CommentReplay implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("评论id")
    @TableField("comment_id")
    private Long commentId;

    @ApiModelProperty("回复id")
    @TableField("reply_id")
    private Long replyId;
}
