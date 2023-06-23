/**
 * Copyright 2019 bejson.com
 */
package com.fz.mall.goods.pojo.dto;

import lombok.Data;


@Data
public class BaseAttrs {

    private Long attrId;
    private String attrValues;
    private Integer showDesc;
    private Integer searchType;


}
