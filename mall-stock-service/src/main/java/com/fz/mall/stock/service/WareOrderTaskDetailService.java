package com.fz.mall.stock.service;

import com.fz.mall.stock.pojo.dto.LockStockTaskDTO;
import com.fz.mall.stock.pojo.entity.WareOrderTaskDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 库存工作单 服务类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
public interface WareOrderTaskDetailService extends IService<WareOrderTaskDetail> {

    LockStockTaskDTO getLockStockDTOByOrderSn(String orderSn);

    void updateOrderStockItemStatus(List<Long> ids, Integer status);

}
