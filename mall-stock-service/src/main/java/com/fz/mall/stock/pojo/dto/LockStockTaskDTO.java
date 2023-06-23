package com.fz.mall.stock.pojo.dto;

import com.fz.mall.stock.pojo.entity.WareOrderTaskDetail;
import lombok.Data;

import java.util.List;

/**
 * 锁库存实体类
 */
@Data
public class LockStockTaskDTO {

    /**
     * 库存工作单id
     */
    private Long id;

    /**
     * 订单号
     */
    private String orderSn;

    private List<WareOrderTaskDetail> wareOrderTaskDetails;
}
