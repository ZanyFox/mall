package com.fz.mall.goods.pojo.dto;


import com.fz.mall.common.pojo.dto.SimplePageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SpuPageDTO extends SimplePageDTO {

    private Long categoryId;
    private Long brandId;
    private Integer status;
}
