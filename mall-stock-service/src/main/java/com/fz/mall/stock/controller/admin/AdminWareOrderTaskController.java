package com.fz.mall.stock.controller.admin;

import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.common.resp.ServRespEntity;
import com.fz.mall.stock.pojo.entity.WareOrderTask;
import com.fz.mall.stock.service.WareOrderTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 库存工作单 前端控制器
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@RestController
@RequestMapping("/admin/stock/ware-order-task")
public class AdminWareOrderTaskController {

    @Autowired
    private WareOrderTaskService wareOrderTaskService;

    @GetMapping("/list")
    public ServRespEntity list(SimplePageDTO simplePageDTO) {
        PageVO<WareOrderTask> page = wareOrderTaskService.page(simplePageDTO);
        return ServRespEntity.success(page);
    }

    @PostMapping
    public ServRespEntity save(@RequestBody WareOrderTask wareOrderTask) {
        wareOrderTaskService.save(wareOrderTask);
        return ServRespEntity.success();
    }


    @PutMapping
    public ServRespEntity update(@RequestBody WareOrderTask wareOrderTask) {
        wareOrderTaskService.updateById(wareOrderTask);
        return ServRespEntity.success();
    }

    @DeleteMapping
    public ServRespEntity delete(@RequestBody List<Long> ids) {
        wareOrderTaskService.removeBatchByIds(ids);
        return ServRespEntity.success();
    }
}
