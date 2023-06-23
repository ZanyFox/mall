package com.fz.mall.goods.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
/**
 * <p>
 * 属性分组
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
@TableName("pms_attr_group")
@ApiModel(value = "AttrGroup对象", description = "属性分组")
public class AttrGroup implements Serializable{

    @ApiModelProperty("分组id")
    @TableId(value = "attr_group_id")
    private Long attrGroupId;

    @ApiModelProperty("组名")
    @TableField("attr_group_name")
    private String attrGroupName;

    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("描述")
    @TableField("descript")
    private String description;

    @ApiModelProperty("组图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty("所属分类id")
    @TableField("catelog_id")
    private Long categoryId;

    @TableField(exist = false)
    private Long[] categoryPath;
}
