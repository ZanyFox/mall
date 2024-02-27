package com.fz.mall.stock.controller.admin;

import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.common.resp.ServRespEntity;
import com.fz.mall.stock.pojo.entity.WareInfo;
import com.fz.mall.stock.service.WareInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 仓库信息 前端控制器
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@RestController
@RequestMapping("/admin/stock/ware-info")
public class AdminWareInfoController {

    @Autowired
    private WareInfoService wareInfoService;

    @PostMapping
    public ServRespEntity save(@RequestBody WareInfo wareInfo) {
        wareInfoService.save(wareInfo);
        return ServRespEntity.success();
    }

    @GetMapping("/list")
    public ServRespEntity page(SimplePageDTO simplePageDTO) {

        PageVO<WareInfo> page = wareInfoService.list(simplePageDTO);
        return ServRespEntity.success(page);
    }

    @DeleteMapping
    public ServRespEntity delete(@RequestBody List<Long> ids) {
        wareInfoService.removeBatchByIds(ids);
        return ServRespEntity.success();
    }

    @PutMapping
    public ServRespEntity update(@RequestBody WareInfo wareInfo) {
        wareInfoService.updateById(wareInfo);
        return ServRespEntity.success();
    }
}
