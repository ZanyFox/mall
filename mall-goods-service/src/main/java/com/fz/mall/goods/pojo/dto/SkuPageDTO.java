package com.fz.mall.goods.pojo.dto;

import com.fz.mall.common.pojo.dto.SimplePageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SkuPageDTO extends SimplePageDTO {

    private Float min;
    private Float max;
    private Long categoryId;
    private Long brandId;
}
