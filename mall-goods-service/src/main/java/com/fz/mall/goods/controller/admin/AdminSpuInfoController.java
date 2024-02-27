package com.fz.mall.goods.controller.admin;

import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.common.resp.ServRespEntity;
import com.fz.mall.goods.pojo.dto.SpuPageDTO;
import com.fz.mall.goods.pojo.dto.SpuSaveDTO;
import com.fz.mall.goods.pojo.dto.UpdateSpuStatusDTO;
import com.fz.mall.goods.pojo.entity.SpuInfo;
import com.fz.mall.goods.service.SpuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admin/goods/spu-info")
public class AdminSpuInfoController {

    @Autowired
    private SpuInfoService spuInfoService;

    @GetMapping("/list")
    public ServRespEntity list(SpuPageDTO spuPageDTO) {
        PageVO<SpuInfo> pageVO = spuInfoService.list(spuPageDTO);
        return ServRespEntity.success(pageVO);
    }

    @PostMapping
    public ServRespEntity save(@RequestBody SpuSaveDTO spuSaveDTO) {
        spuInfoService.save(spuSaveDTO);
        return ServRespEntity.success();
    }

    @PutMapping("/status")
    public ServRespEntity update(@Validated @RequestBody UpdateSpuStatusDTO updateSpuStatusDTO) {
        spuInfoService.updateSpuStatus(updateSpuStatusDTO);
        return ServRespEntity.success();
    }

}
