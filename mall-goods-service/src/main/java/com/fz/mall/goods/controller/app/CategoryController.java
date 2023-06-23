package com.fz.mall.goods.controller.app;

import com.fz.mall.common.resp.ServerResponseEntity;
import com.fz.mall.goods.pojo.entity.Category;
import com.fz.mall.goods.pojo.vo.CategoryTitleVO;
import com.fz.mall.goods.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品三级分类 前端控制器
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@RestController
@RequestMapping("/goods/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/list")
    public ServerResponseEntity<?> listAll() {
        List<Category> categories = categoryService.listAll();
        return ServerResponseEntity.success(categories);
    }

    @GetMapping("/menu")
    public Map<String, List<CategoryTitleVO>> getCategory() {
        return categoryService.getCategoryTitleMap();

    }

    @PostMapping("/delete")
    public ServerResponseEntity<Void> delete(@RequestBody Long[] ids) {
        categoryService.removeCategoryByIds(Arrays.asList(ids));
        return ServerResponseEntity.success();
    }

    @PostMapping("/save")
    public ServerResponseEntity<Void> save() {
        return ServerResponseEntity.success();
    }

}
