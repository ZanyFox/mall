package com.fz.mall.stock.mq;

import com.fz.mall.common.rabbitmq.constant.StockMqConstants;
import com.fz.mall.stock.pojo.dto.LockStockTaskDTO;
import com.fz.mall.stock.service.WareOrderTaskDetailService;
import com.fz.mall.stock.service.WareSkuService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
@RabbitListener(queues = StockMqConstants.UNLOCK_STOCK_QUEUE)
public class StockUnlockListener {

    private WareSkuService wareSkuService;

    private WareOrderTaskDetailService wareOrderTaskDetailService;


    /**
     * 订单超时关闭时解锁库存
     * @param orderSn
     */
    @RabbitHandler
    public void orderCloseStockUnlockHandler(String orderSn) {
        log.info("订单关闭，解锁库存: {}", orderSn);
        LockStockTaskDTO lockStockDTO = wareOrderTaskDetailService.getLockStockDTOByOrderSn(orderSn);
        wareSkuService.unlockStock(lockStockDTO);
    }
}
