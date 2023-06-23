package com.fz.mall.stock.service.impl;

import com.fz.mall.stock.pojo.dto.LockStockTaskDTO;
import com.fz.mall.stock.pojo.entity.WareOrderTaskDetail;
import com.fz.mall.stock.mapper.WareOrderTaskDetailMapper;
import com.fz.mall.stock.service.WareOrderTaskDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WareOrderTaskDetailServiceImpl extends ServiceImpl<WareOrderTaskDetailMapper, WareOrderTaskDetail> implements WareOrderTaskDetailService {


    private WareOrderTaskDetailMapper wareOrderTaskDetailMapper;

    @Override
    public LockStockTaskDTO getLockStockDTOByOrderSn(String orderSn) {

        List<WareOrderTaskDetail> wareOrderTaskDetails =
                wareOrderTaskDetailMapper.getWareOrderTaskDetailByOrderSn(orderSn);
        LockStockTaskDTO lockStockTaskDTO = new LockStockTaskDTO();
        lockStockTaskDTO.setWareOrderTaskDetails(wareOrderTaskDetails);
        lockStockTaskDTO.setOrderSn(orderSn);
        return lockStockTaskDTO;
    }

    @Override
    public void updateOrderStockItemStatus(List<Long> ids, Integer status) {
        wareOrderTaskDetailMapper.updateStockItemStatus(ids, status);
    }
}
