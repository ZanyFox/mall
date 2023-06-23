package com.fz.mall.order.mq;

import com.fz.mall.common.rabbitmq.constant.OrderMqConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderMqConfig {

    @Bean
    public DirectExchange orderExchange() {
        return ExchangeBuilder.directExchange(OrderMqConstants.ORDER_EXCHANGE)
                .delayed().build();
    }


    @Bean
    public Queue orderCloseQueue() {
        return QueueBuilder.durable(OrderMqConstants.ORDER_CLOSE_QUEUE).build();
    }


    @Bean
    public Queue orderCreateQueue() {
        return QueueBuilder.durable(OrderMqConstants.ORDER_CREATE_QUEUE).build();
    }

    @Bean
    public Queue orderSeckillCreateQueue() {
        return QueueBuilder.durable(OrderMqConstants.ORDER_SECKILL_CREATE_QUEUE).build();
    }


    @Bean
    public Binding orderCloseBinding(DirectExchange orderExchange, Queue orderCloseQueue) {
        return BindingBuilder.bind(orderCloseQueue).to(orderExchange).with(OrderMqConstants.ORDER_CLOSE_ROUTING_KEY);
    }


    @Bean
    public Binding orderCreateBinding(DirectExchange orderExchange, Queue orderCreateQueue) {
        return BindingBuilder.bind(orderCreateQueue).to(orderExchange).with(OrderMqConstants.ORDER_CREATE_ROUTING_KEY);
    }

    @Bean
    public Binding orderSeckillCreateBinding(DirectExchange orderExchange, Queue orderSeckillCreateQueue) {
        return BindingBuilder.bind(orderSeckillCreateQueue).to(orderExchange).with(OrderMqConstants.ORDER_SECKILL_CREATE_ROUTING_KEY);
    }
}
