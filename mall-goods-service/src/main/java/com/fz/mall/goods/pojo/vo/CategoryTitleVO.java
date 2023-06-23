package com.fz.mall.goods.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * 二级分类
 */
@Data
public class CategoryTitleVO {

    private Long id;

    private String name;

    /**
     * 一级父分类id
     */
    private Long CategoryMenuId;
    private List<CategoryDetailVO> categoryDetailList;

}
