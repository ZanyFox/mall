package com.fz.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fz.mall.goods.pojo.entity.Category;
import com.fz.mall.goods.pojo.vo.CategoryTitleVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品三级分类 服务类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
public interface CategoryService extends IService<Category> {

    List<Category> listAll();

    void removeCategoryByIds(List<Long> ids);

    /**
     * 获取一级分类
     */
    List<Category> getCategoryMenuList();

    /**
     * @return 一级分类id作为Map的key  子分类作为value
     */
    Map<String, List<CategoryTitleVO>> getCategoryTitleMap();

    /**
     * 级联更新数据
     * @param category
     */
    void updateCascade(Category category);

    Long[] findCategoryPath(Long categoryId);


}
