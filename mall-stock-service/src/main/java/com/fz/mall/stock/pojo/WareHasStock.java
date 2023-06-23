package com.fz.mall.stock.pojo;

import lombok.Data;

import java.util.List;

@Data
public class WareHasStock {

    private Long skuId;

    private List<Long> wareIds;

}
