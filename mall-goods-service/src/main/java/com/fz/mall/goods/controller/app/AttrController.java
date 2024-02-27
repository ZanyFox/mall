package com.fz.mall.goods.controller.app;

import com.fz.mall.common.resp.ServRespEntity;
import com.fz.mall.goods.pojo.entity.ProductAttrValue;
import com.fz.mall.goods.service.ProductAttrValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品属性 前端控制器
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@RestController
@RequestMapping("/goods/attr")
public class AttrController {


    @Autowired
    private ProductAttrValueService productAttrValueService;

    @GetMapping("/base/{spuId}")
    public ServRespEntity listBaseSpuAttr(@PathVariable Long spuId) {

        List<ProductAttrValue> productAttrValues = productAttrValueService.listBaseAttrById(spuId);
        return ServRespEntity.success(productAttrValues);
    }

    @PutMapping("/update/{spuId}")
    public ServRespEntity updateSpuAttr(@PathVariable Long spuId, List<ProductAttrValue> productAttrValues) {

        productAttrValueService.updateSpuAttr(spuId, productAttrValues);
        return ServRespEntity.success();
    }

}
