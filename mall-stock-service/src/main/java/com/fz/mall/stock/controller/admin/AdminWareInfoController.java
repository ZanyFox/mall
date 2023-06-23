package com.fz.mall.stock.controller.admin;

import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.common.resp.ServerResponseEntity;
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
    public ServerResponseEntity save(@RequestBody WareInfo wareInfo) {
        wareInfoService.save(wareInfo);
        return ServerResponseEntity.success();
    }

    @GetMapping("/list")
    public ServerResponseEntity page(SimplePageDTO simplePageDTO) {

        PageVO<WareInfo> page = wareInfoService.list(simplePageDTO);
        return ServerResponseEntity.success(page);
    }

    @DeleteMapping
    public ServerResponseEntity delete(@RequestBody List<Long> ids) {
        wareInfoService.removeBatchByIds(ids);
        return ServerResponseEntity.success();
    }

    @PutMapping
    public ServerResponseEntity update(@RequestBody WareInfo wareInfo) {
        wareInfoService.updateById(wareInfo);
        return ServerResponseEntity.success();
    }
}
