package com.fz.mall.goods.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fz.mall.common.resp.ServerResponseEntity;
import com.fz.mall.goods.pojo.entity.CategoryBrandRelation;
import com.fz.mall.goods.service.CategoryBrandRelationService;
import com.fz.mall.goods.pojo.vo.BrandVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 品牌分类关联 前端控制器
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@RestController
@RequestMapping("/admin/goods/category-brand-relation")
public class AdminCategoryBrandRelationController {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    /**
     *
     */
    @GetMapping("/list")
    public ServerResponseEntity list(@RequestParam("brandId") Long brandId) {

        LambdaQueryWrapper<CategoryBrandRelation> queryWrapper = new LambdaQueryWrapper<>();
        List<CategoryBrandRelation> relations =
                categoryBrandRelationService.list(queryWrapper.eq(CategoryBrandRelation::getBrandId, brandId));
        return ServerResponseEntity.success(relations);
    }

    @GetMapping("/brand/list")
    private ServerResponseEntity brandList(@RequestParam("categoryId") Long categoryId) {
        List<BrandVO> brands = categoryBrandRelationService.listBrand(categoryId);
        return ServerResponseEntity.success(brands);
    }

    @PostMapping
    public ServerResponseEntity save(@RequestBody CategoryBrandRelation categoryBrandRelation) {
        categoryBrandRelationService.saveDetail(categoryBrandRelation);
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    public ServerResponseEntity delete(@RequestBody Long[] ids) {
        categoryBrandRelationService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseEntity.success();
    }

}
