package com.fz.mall.goods.controller.admin;

import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.common.resp.ServerResponseEntity;
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
    public ServerResponseEntity list(SimplePageDTO simplePageDTO) {
        PageVO<Brand> page = brandService.page(simplePageDTO);
        return ServerResponseEntity.success(page);
    }

    @GetMapping("/{brandId}")
    public ServerResponseEntity<Brand> info(@PathVariable("brandId") Long brandId) {

        Brand brand = brandService.getById(brandId);
        return ServerResponseEntity.success(brand);
    }

    @PostMapping
    public ServerResponseEntity save(@RequestBody Brand brand) {
        brandService.save(brand);
        return ServerResponseEntity.success();
    }

    @PutMapping
    public ServerResponseEntity update(@RequestBody Brand brand) {
        brandService.updateDetail(brand);
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    public ServerResponseEntity delete(@RequestBody Long[] ids) {
        log.info(Arrays.toString(ids));
        brandService.removeBatchByIds(Arrays.asList(ids));
        return ServerResponseEntity.success();
    }

}
