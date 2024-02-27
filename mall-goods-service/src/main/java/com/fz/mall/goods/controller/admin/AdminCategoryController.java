package com.fz.mall.goods.controller.admin;

import com.fz.mall.common.resp.ServRespEntity;
import com.fz.mall.goods.pojo.entity.Category;
import com.fz.mall.goods.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 商品三级分类 前端控制器
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@RestController
@RequestMapping("/admin/goods/category")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/list")
    public ServRespEntity<?> list() {
        List<Category> categories = categoryService.listAll();
        return ServRespEntity.success(categories);
    }

    @GetMapping("/info/{id}")
    public ServRespEntity<?> info(@PathVariable Long id) {
        Category category = categoryService.getById(id);
        return ServRespEntity.success(category);
    }

    @PostMapping("/delete")
    public ServRespEntity<Void> delete(@RequestBody Long[] ids) {
        categoryService.removeCategoryByIds(Arrays.asList(ids));
        return ServRespEntity.success();
    }



    @PostMapping("/save")
    public ServRespEntity<Void> save(@RequestBody Category category) {
        categoryService.save(category);
        return ServRespEntity.success();
    }

    @PostMapping("/update")
    public ServRespEntity<Void> update(@RequestBody Category category) {

        categoryService.updateCascade(category);
        return ServRespEntity.success();
    }

    /**
     * 拖拽分类后调用此接口
     * @param categories
     * @return
     */
    @PostMapping("/update/sort")
    public ServRespEntity updateSort(@RequestBody Category[] categories) {
        categoryService.updateBatchById(Arrays.asList(categories));
        return ServRespEntity.success();
    }
}
