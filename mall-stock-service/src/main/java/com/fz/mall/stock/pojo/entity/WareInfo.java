package com.fz.mall.stock.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
/**
 * <p>
 * 仓库信息
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@Data
@TableName("wms_ware_info")
@ApiModel(value = "WareInfo对象", description = "仓库信息")
public class WareInfo implements Serializable{

    @ApiModelProperty("id")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("仓库名")
    @TableField("name")
    private String name;

    @ApiModelProperty("仓库地址")
    @TableField("address")
    private String address;

    @ApiModelProperty("区域编码")
    @TableField("areacode")
    @JsonProperty("areacode")
    private String areaCode;
}
