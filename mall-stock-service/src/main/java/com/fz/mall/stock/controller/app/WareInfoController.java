package com.fz.mall.stock.controller.app;

import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.common.resp.ServRespEntity;
import com.fz.mall.stock.pojo.entity.WareInfo;
import com.fz.mall.stock.service.WareInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 仓库信息 前端控制器
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@RestController
@RequestMapping("/stock/ware-info")
public class WareInfoController {

    @Autowired
    private WareInfoService wareInfoService;

    @PostMapping
    public ServRespEntity save(@RequestBody WareInfo wareInfo) {
        wareInfoService.save(wareInfo);
        return ServRespEntity.success();
    }

    @GetMapping
    public ServRespEntity page(SimplePageDTO simplePageDTO) {

        PageVO<WareInfo> page = wareInfoService.list(simplePageDTO);
        return ServRespEntity.success(page);
    }
}
