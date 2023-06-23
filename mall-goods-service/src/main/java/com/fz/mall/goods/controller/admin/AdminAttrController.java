package com.fz.mall.goods.controller.admin;

import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.common.resp.ServerResponseEntity;
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
    public ServerResponseEntity listAll(
            @PathVariable String attrType,
            SimplePageDTO simplePageDTO) {


        PageVO<AttrVO> page = attrService.page(null, attrType, simplePageDTO);
        return ServerResponseEntity.success(page);
    }

    @GetMapping("/base/spu/{spuId}")
    public ServerResponseEntity listSpuAttr(@PathVariable Long spuId) {

        List<ProductAttrValue> productAttrValues = attrService.listSpuAttr(spuId);
        return ServerResponseEntity.success(productAttrValues);
    }


    @GetMapping("/{attrType}/list/{categoryId}")
    public ServerResponseEntity list(@PathVariable Long categoryId,
                                     @PathVariable String attrType,
                                     SimplePageDTO simplePageDTO) {

        PageVO<AttrVO> page = attrService.page(categoryId, attrType, simplePageDTO);
        return ServerResponseEntity.success(page);
    }

    @GetMapping("/{attrId}")
    public ServerResponseEntity getAttrById(@PathVariable Long attrId) {

        AttrVO attrVO = attrService.getAttrInfo(attrId);
        return ServerResponseEntity.success(attrVO);
    }

    @PostMapping
    public ServerResponseEntity save(@RequestBody AttrDTO attrDTO) {
        attrService.save(attrDTO);
        return ServerResponseEntity.success();
    }


    @PutMapping
    public ServerResponseEntity update(@RequestBody AttrDTO attrDTO) {
        attrService.update(attrDTO);
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    public ServerResponseEntity delete(@RequestBody Long[] ids) {
        attrService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseEntity.success();
    }


    @PutMapping("/spu/{spuId}")
    public ServerResponseEntity updateSpuAttr(@PathVariable Long spuId, @RequestBody List<ProductAttrValue> productAttrValues) {

        productAttrValueService.updateSpuAttr(spuId, productAttrValues);
        return ServerResponseEntity.success();
    }
}
