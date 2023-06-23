package com.fz.mall.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
/**
 * <p>
 * 会员统计信息
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
@TableName("ums_member_statistics_info")
@ApiModel(value = "MemberStatisticsInfo对象", description = "会员统计信息")
public class MemberStatisticsInfo implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("会员id")
    @TableField("member_id")
    private Long memberId;

    @ApiModelProperty("累计消费金额")
    @TableField("consume_amount")
    private BigDecimal consumeAmount;

    @ApiModelProperty("累计优惠金额")
    @TableField("coupon_amount")
    private BigDecimal couponAmount;

    @ApiModelProperty("订单数量")
    @TableField("order_count")
    private Integer orderCount;

    @ApiModelProperty("优惠券数量")
    @TableField("coupon_count")
    private Integer couponCount;

    @ApiModelProperty("评价数")
    @TableField("comment_count")
    private Integer commentCount;

    @ApiModelProperty("退货数量")
    @TableField("return_order_count")
    private Integer returnOrderCount;

    @ApiModelProperty("登录次数")
    @TableField("login_count")
    private Integer loginCount;

    @ApiModelProperty("关注数量")
    @TableField("attend_count")
    private Integer attendCount;

    @ApiModelProperty("粉丝数量")
    @TableField("fans_count")
    private Integer fansCount;

    @ApiModelProperty("收藏的商品数量")
    @TableField("collect_product_count")
    private Integer collectProductCount;

    @ApiModelProperty("收藏的专题活动数量")
    @TableField("collect_subject_count")
    private Integer collectSubjectCount;

    @ApiModelProperty("收藏的评论数量")
    @TableField("collect_comment_count")
    private Integer collectCommentCount;

    @ApiModelProperty("邀请的朋友数量")
    @TableField("invite_friend_count")
    private Integer inviteFriendCount;
}
