package com.fz.mall.search.listener;

import com.fz.mall.common.rabbitmq.constant.GoodsListenerConstants;
import com.fz.mall.search.service.impl.EsSpuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SearchGoodsListener {

    @Autowired
    private EsSpuService esSpuService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(GoodsListenerConstants.SEARCH__GOODS_INSERT_QUEUE),
            exchange = @Exchange(GoodsListenerConstants.SEARCH_GOODS_EXCHANGE),
            key = {GoodsListenerConstants.SEARCH__GOODS_INSERT_ROUTING_KEY}
    ))
    public void spuInsertListener(Long spuId) {

        esSpuService.saveEsSpuBO(spuId);
    }
}
