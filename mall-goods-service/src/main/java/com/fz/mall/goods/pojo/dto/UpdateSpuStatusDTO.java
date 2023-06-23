package com.fz.mall.goods.pojo.dto;

import com.fz.mall.common.validator.EnumValue;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateSpuStatusDTO {

    @NotNull(message = "更新时id不可以为空呦~")
    private Long spuId;
    @EnumValue(intValues = {0, 1, 2}, message = "状态只能是0、1、2")
    private Integer status;
}
