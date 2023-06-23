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
 * 属性&属性分组关联
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
@TableName("pms_attr_attrgroup_relation")
@ApiModel(value = "AttrAttrgroupRelation对象", description = "属性&属性分组关联")
public class AttrAttrGroupRelation implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("属性id")
    @TableField("attr_id")
    private Long attrId;

    @ApiModelProperty("属性分组id")
    @TableField("attr_group_id")
    private Long attrGroupId;

    @ApiModelProperty("属性组内排序")
    @TableField("attr_sort")
    private Integer attrSort;
}
