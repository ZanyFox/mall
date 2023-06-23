package com.fz.mall.stock.controller.admin;

import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.common.resp.ServerResponseEntity;
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
    public ServerResponseEntity list(SimplePageDTO simplePageDTO) {
        PageVO<WareOrderTask> page = wareOrderTaskService.page(simplePageDTO);
        return ServerResponseEntity.success(page);
    }

    @PostMapping
    public ServerResponseEntity save(@RequestBody WareOrderTask wareOrderTask) {
        wareOrderTaskService.save(wareOrderTask);
        return ServerResponseEntity.success();
    }


    @PutMapping
    public ServerResponseEntity update(@RequestBody WareOrderTask wareOrderTask) {
        wareOrderTaskService.updateById(wareOrderTask);
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    public ServerResponseEntity delete(@RequestBody List<Long> ids) {
        wareOrderTaskService.removeBatchByIds(ids);
        return ServerResponseEntity.success();
    }
}
