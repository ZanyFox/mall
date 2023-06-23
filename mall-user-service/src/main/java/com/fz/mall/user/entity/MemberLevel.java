package com.fz.mall.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * <p>
 * 会员等级
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
@TableName("ums_member_level")
@ApiModel(value = "MemberLevel对象", description = "会员等级")
public class MemberLevel implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("等级名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("等级需要的成长值")
    @TableField("growth_point")
    private Integer growthPoint;

    @ApiModelProperty("是否为默认等级[0->不是；1->是]")
    @TableField("default_status")
    private Byte defaultStatus;

    @ApiModelProperty("免运费标准")
    @TableField("free_freight_point")
    private BigDecimal freeFreightPoint;

    @ApiModelProperty("每次评价获取的成长值")
    @TableField("comment_growth_point")
    private Integer commentGrowthPoint;

    @ApiModelProperty("是否有免邮特权")
    @TableField("privilege_free_freight")
    private Byte privilegeFreeFreight;

    @ApiModelProperty("是否有会员价格特权")
    @TableField("privilege_member_price")
    private Byte privilegeMemberPrice;

    @ApiModelProperty("是否有生日特权")
    @TableField("privilege_birthday")
    private Byte privilegeBirthday;

    @ApiModelProperty("备注")
    @TableField("note")
    private String note;
}
