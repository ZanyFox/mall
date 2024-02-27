package com.fz.mall.goods.controller.admin;

import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.common.resp.ServRespEntity;
import com.fz.mall.goods.pojo.dto.AttrDTO;
import com.fz.mall.goods.pojo.entity.ProductAttrValue;
import com.fz.mall.goods.pojo.vo.AttrVO;
import com.fz.mall.goods.service.AttrService;
import com.fz.mall.goods.service.ProductAttrValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 商品属性 前端控制器
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@RestController()
@RequestMapping("/admin/goods/attr")
public class AdminAttrController {

    @Autowired
    private AttrService attrService;

    @Autowired
    private ProductAttrValueService productAttrValueService;

    @GetMapping("/{attrType}/list")
    public ServRespEntity listAll(
            @PathVariable String attrType,
            SimplePageDTO simplePageDTO) {


        PageVO<AttrVO> page = attrService.page(null, attrType, simplePageDTO);
        return ServRespEntity.success(page);
    }

    @GetMapping("/base/spu/{spuId}")
    public ServRespEntity listSpuAttr(@PathVariable Long spuId) {

        List<ProductAttrValue> productAttrValues = attrService.listSpuAttr(spuId);
        return ServRespEntity.success(productAttrValues);
    }


    @GetMapping("/{attrType}/list/{categoryId}")
    public ServRespEntity list(@PathVariable Long categoryId,
                               @PathVariable String attrType,
                               SimplePageDTO simplePageDTO) {

        PageVO<AttrVO> page = attrService.page(categoryId, attrType, simplePageDTO);
        return ServRespEntity.success(page);
    }

    @GetMapping("/{attrId}")
    public ServRespEntity getAttrById(@PathVariable Long attrId) {

        AttrVO attrVO = attrService.getAttrInfo(attrId);
        return ServRespEntity.success(attrVO);
    }

    @PostMapping
    public ServRespEntity save(@RequestBody AttrDTO attrDTO) {
        attrService.save(attrDTO);
        return ServRespEntity.success();
    }


    @PutMapping
    public ServRespEntity update(@RequestBody AttrDTO attrDTO) {
        attrService.update(attrDTO);
        return ServRespEntity.success();
    }

    @DeleteMapping
    public ServRespEntity delete(@RequestBody Long[] ids) {
        attrService.removeBatchByIds(Arrays.asList(ids));
        return ServRespEntity.success();
    }


    @PutMapping("/spu/{spuId}")
    public ServRespEntity updateSpuAttr(@PathVariable Long spuId, @RequestBody List<ProductAttrValue> productAttrValues) {

        productAttrValueService.updateSpuAttr(spuId, productAttrValues);
        return ServRespEntity.success();
    }
}
