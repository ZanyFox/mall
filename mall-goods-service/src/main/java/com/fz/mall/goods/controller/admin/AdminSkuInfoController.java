package com.fz.mall.goods.controller.admin;

import com.fz.mall.api.goods.dto.SkuInfoDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.common.resp.ServerResponseEntity;
import com.fz.mall.goods.pojo.dto.SkuPageDTO;
import com.fz.mall.goods.pojo.entity.SkuInfo;
import com.fz.mall.goods.service.SkuInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * sku信息 前端控制器
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@RestController
@RequestMapping("/admin/goods/sku-info")
public class AdminSkuInfoController {

    @Autowired
    private SkuInfoService skuInfoService;



    @GetMapping("/{skuId}")
    public ServerResponseEntity getSkuInfoBySkuId(@PathVariable Long skuId) {
        SkuInfo skuInfo = skuInfoService.getById(skuId);
        SkuInfoDTO skuInfoDTO = new SkuInfoDTO();
        BeanUtils.copyProperties(skuInfo, skuInfoDTO);
        return ServerResponseEntity.success(skuInfoDTO);
    }

    @GetMapping("/list")
    public ServerResponseEntity list(SkuPageDTO skuDTO) {
        PageVO<SkuInfo> page = skuInfoService.page(skuDTO);
        return ServerResponseEntity.success(page);
    }



}
