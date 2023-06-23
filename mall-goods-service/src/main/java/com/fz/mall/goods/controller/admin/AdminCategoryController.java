package com.fz.mall.goods.controller.admin;

import com.fz.mall.common.resp.ServerResponseEntity;
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
    public ServerResponseEntity<?> list() {
        List<Category> categories = categoryService.listAll();
        return ServerResponseEntity.success(categories);
    }

    @GetMapping("/info/{id}")
    public ServerResponseEntity<?> info(@PathVariable Long id) {
        Category category = categoryService.getById(id);
        return ServerResponseEntity.success(category);
    }

    @PostMapping("/delete")
    public ServerResponseEntity<Void> delete(@RequestBody Long[] ids) {
        categoryService.removeCategoryByIds(Arrays.asList(ids));
        return ServerResponseEntity.success();
    }



    @PostMapping("/save")
    public ServerResponseEntity<Void> save(@RequestBody Category category) {
        categoryService.save(category);
        return ServerResponseEntity.success();
    }

    @PostMapping("/update")
    public ServerResponseEntity<Void> update(@RequestBody Category category) {

        categoryService.updateCascade(category);
        return ServerResponseEntity.success();
    }

    /**
     * 拖拽分类后调用此接口
     * @param categories
     * @return
     */
    @PostMapping("/update/sort")
    public ServerResponseEntity updateSort(@RequestBody Category[] categories) {
        categoryService.updateBatchById(Arrays.asList(categories));
        return ServerResponseEntity.success();
    }
}
