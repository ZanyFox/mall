package com.fz.mall.goods.controller.mvc;

import com.fz.mall.goods.pojo.entity.Category;
import com.fz.mall.goods.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@Controller
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping({"/index.html", "/", "index"})
    public String index(Model model) {

        List<Category> categories = categoryService.getCategoryMenuList();
        model.addAttribute("categories", categories);
        model.addAttribute("name", "Alan");
        log.info("当前线程：{}", Thread.currentThread().getName());
        return "index";
    }


}
