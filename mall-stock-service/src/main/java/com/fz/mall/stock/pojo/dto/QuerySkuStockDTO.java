package com.fz.mall.stock.pojo.dto;

import com.fz.mall.common.pojo.dto.SimplePageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuerySkuStockDTO extends SimplePageDTO {

    private Long wareId;
    private Long skuId;
}
