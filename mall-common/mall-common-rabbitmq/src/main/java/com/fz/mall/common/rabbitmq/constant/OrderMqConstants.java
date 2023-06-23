package com.fz.mall.common.rabbitmq.constant;

public class OrderMqConstants {

    public static final Integer ORDER_CLOSE_DELAY_TIME = 60 * 1000;

    public static final String ORDER_EXCHANGE = "order.exchange";

    public static final String ORDER_DELAY_CLOSE_EXCHANGE = "order.delay.close.exchange";

    public static final String ORDER_ROUTING_KEY = "order.#";

    public static final String ORDER_DELAY_CLOSE_ROUTING_KEY = "order.delay.close";

    public static final String ORDER_CLOSE_ROUTING_KEY = "order.close";

    public static final String ORDER_CREATE_ROUTING_KEY = "order.create";

    public static final String ORDER_SECKILL_CREATE_ROUTING_KEY = "order.seckill.create";

    public static final String ORDER_SECKILL_CREATE_QUEUE = "order.seckill.create.queue";

    public static final String ORDER_CLOSE_QUEUE = "order.close.queue";

    public static final String ORDER_CREATE_QUEUE = "order.create.queue";


}
