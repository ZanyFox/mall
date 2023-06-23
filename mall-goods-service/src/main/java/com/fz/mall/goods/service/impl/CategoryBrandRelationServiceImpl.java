package com.fz.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fz.mall.goods.pojo.entity.CategoryBrandRelation;
import com.fz.mall.goods.mapper.CategoryBrandRelationMapper;
import com.fz.mall.goods.service.BrandService;
import com.fz.mall.goods.service.CategoryBrandRelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fz.mall.goods.service.CategoryService;
import com.fz.mall.goods.pojo.vo.BrandVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 品牌分类关联 服务实现类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Service
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationMapper, CategoryBrandRelation> implements CategoryBrandRelationService {

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Override
    public List<CategoryBrandRelation> listCategory(Long brandId) {
        LambdaQueryWrapper<CategoryBrandRelation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CategoryBrandRelation::getBrandId, brandId);
        return list(queryWrapper);
    }

    /**
     * 新增时保存冗余字段
     *
     * @param categoryBrandRelation
     */
    @Override
    public void saveDetail(CategoryBrandRelation categoryBrandRelation) {

        Long brandId = categoryBrandRelation.getBrandId();
        Long catelogId = categoryBrandRelation.getCategoryId();
        String brandName = brandService.getById(brandId).getName();
        String categoryName = categoryService.getById(catelogId).getName();
        categoryBrandRelation.setBrandName(brandName);
        categoryBrandRelation.setCategoryName(categoryName);
        save(categoryBrandRelation);
    }

    @Transactional
    @Override
    public void updateBrand(Long brandId, String name) {
        CategoryBrandRelation categoryBrandRelation = new CategoryBrandRelation();
        categoryBrandRelation.setBrandName(name);
        categoryBrandRelation.setBrandId(brandId);
        lambdaUpdate().eq(CategoryBrandRelation::getBrandId, brandId).update(categoryBrandRelation);
    }

    @Override
    public void updateCategory(Long catId, String name) {
        CategoryBrandRelation categoryBrandRelation = new CategoryBrandRelation();
        categoryBrandRelation.setCategoryName(name);
        categoryBrandRelation.setCategoryId(catId);
        update(categoryBrandRelation, new LambdaUpdateWrapper<CategoryBrandRelation>().eq(CategoryBrandRelation::getCategoryId, catId));
    }

    @Override
    public List<BrandVO> listBrand(Long categoryId) {

        List<CategoryBrandRelation> relations = lambdaQuery().eq(CategoryBrandRelation::getCategoryId, categoryId).list();
        return relations.stream().map(relation -> {
            BrandVO brandVO = new BrandVO();
            BeanUtils.copyProperties(relation, brandVO);
            return brandVO;
        }).collect(Collectors.toList());
    }
}
