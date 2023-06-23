package com.fz.mall.search.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class SearchResultVO {


    private List<SearchSkuVO> goods;

    private List<BrandVO> brands;

    private List<AttrVO> attrs;

    private List<CategoryVO> categories;

    private Integer total;

    private Integer page;

    private Integer size;

    private Integer pages;
}
