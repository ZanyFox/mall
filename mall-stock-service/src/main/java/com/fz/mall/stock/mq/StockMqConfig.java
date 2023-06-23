package com.fz.mall.stock.mq;

import com.fz.mall.common.rabbitmq.constant.StockMqConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StockMqConfig {

    @Bean
    public TopicExchange stockExchange() {
        return ExchangeBuilder.topicExchange(StockMqConstants.STOCK_EXCHANGE).build();
    }

    @Bean
    public Queue stockDelayQueue() {
        return QueueBuilder.durable(StockMqConstants.UNLOCK_STOCK_DELAY_QUEUE)
                .deadLetterExchange(StockMqConstants.STOCK_EXCHANGE)
                .deadLetterRoutingKey(StockMqConstants.UNLOCK_STOCK_ROUTING_KEY)
                .ttl(2 * 60 * 1000)
                .build();
    }

    @Bean
    public Queue stockDeductQueue() {
        return QueueBuilder.durable(StockMqConstants.DEDUCT_STOCK_QUEUE)
                .build();
    }

    @Bean
    public Queue stockUnlockQueue() {
        return QueueBuilder.durable(StockMqConstants.UNLOCK_STOCK_QUEUE)
                .build();
    }

    @Bean
    public Binding stockDelayUnlockBinding(TopicExchange stockExchange, Queue stockDelayQueue) {
        return BindingBuilder.bind(stockDelayQueue).to(stockExchange).with(StockMqConstants.UNLOCK_STOCK_DELAY_ROUTING_KEY);
    }

    @Bean
    public Binding stockUnlockBinding(TopicExchange stockExchange, Queue stockUnlockQueue) {
        return BindingBuilder.bind(stockUnlockQueue).to(stockExchange).with(StockMqConstants.UNLOCK_STOCK_ROUTING_KEY);
    }

    @Bean
    public Binding stockDeductBinding(TopicExchange stockExchange, Queue stockDeductQueue) {
        return BindingBuilder.bind(stockDeductQueue).to(stockExchange).with(StockMqConstants.DEDUCT_STOCK_ROUTING_KEY);
    }
}
