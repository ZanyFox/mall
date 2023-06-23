package com.fz.mall.goods.service;

import com.fz.mall.goods.pojo.entity.CategoryBrandRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fz.mall.goods.pojo.vo.BrandVO;

import java.util.List;

/**
 * <p>
 * 品牌分类关联 服务类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelation> {

    List<CategoryBrandRelation> listCategory(Long brandId);

    /**
     *
     * 保存品牌和种类关系
     */
    void saveDetail(CategoryBrandRelation categoryBrandRelation);

    void updateBrand(Long brandId, String name);

    void updateCategory(Long catId, String name);

    List<BrandVO> listBrand(Long categoryId);
}
