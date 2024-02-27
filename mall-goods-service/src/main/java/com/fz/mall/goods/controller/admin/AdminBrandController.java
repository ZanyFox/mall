package com.fz.mall.goods.controller.admin;

import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.common.resp.ServRespEntity;
import com.fz.mall.goods.pojo.entity.Brand;
import com.fz.mall.goods.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/admin/goods/brand")
public class AdminBrandController {

    @Autowired
    private BrandService brandService;


    @GetMapping("/list")
    public ServRespEntity list(SimplePageDTO simplePageDTO) {
        PageVO<Brand> page = brandService.page(simplePageDTO);
        return ServRespEntity.success(page);
    }

    @GetMapping("/{brandId}")
    public ServRespEntity<Brand> info(@PathVariable("brandId") Long brandId) {

        Brand brand = brandService.getById(brandId);
        return ServRespEntity.success(brand);
    }

    @PostMapping
    public ServRespEntity save(@RequestBody Brand brand) {
        brandService.save(brand);
        return ServRespEntity.success();
    }

    @PutMapping
    public ServRespEntity update(@RequestBody Brand brand) {
        brandService.updateDetail(brand);
        return ServRespEntity.success();
    }

    @DeleteMapping
    public ServRespEntity delete(@RequestBody Long[] ids) {
        log.info(Arrays.toString(ids));
        brandService.removeBatchByIds(Arrays.asList(ids));
        return ServRespEntity.success();
    }

}
