package com.fz.mall.stock.mq;

import com.fz.mall.common.rabbitmq.constant.StockMqConstants;
import com.fz.mall.stock.service.WareSkuService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class StockMqListener {

    private final WareSkuService wareSkuService;

    @RabbitListener(queues = StockMqConstants.DEDUCT_STOCK_QUEUE)
    public void deductStockListener(String orderSn) {
        log.info("收到扣减库存消息: {}", orderSn);
        wareSkuService.deductStock(orderSn);
    }
}
