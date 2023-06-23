package com.fz.mall.goods.controller.mvc;

import com.fz.mall.goods.pojo.vo.SkuItemVO;
import com.fz.mall.goods.service.SkuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ItemController {

    @Autowired
    private SkuItemService skuItemService;

    @GetMapping("/{skuId}.html")
    public String skuItem(@PathVariable Long skuId, Model model) {
        SkuItemVO skuItemVO = skuItemService.getSkuItemById(skuId);
        model.addAttribute("item", skuItemVO);
        return "item";
    }
}
