package com.fz.mall.search.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchParamDTO {

    private String keyword;

    private Long cid;

    private Long cid3;

    private String sort;

    private Boolean hasStock = true;

    private Integer gtePrice;

    private Integer ltePrice;

    private List<Long> brandIds;

    private String attrs;

    private Integer page;

    private Integer size = 60;

    private String skuPrice;
}
