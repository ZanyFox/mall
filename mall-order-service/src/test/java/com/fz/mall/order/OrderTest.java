package com.fz.mall.order;

import com.alibaba.fastjson2.JSON;
import com.fz.mall.api.user.feign.MemberFeignClient;
import com.fz.mall.common.rabbitmq.constant.OrderMqConstants;
import com.fz.mall.order.entity.Order;
import com.fz.mall.order.entity.OrderItem;
import com.fz.mall.order.mapper.OrderItemMapper;
import com.fz.mall.order.service.OrderItemService;
import com.fz.mall.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderTest {

    @Autowired
    private MemberFeignClient memberFeignClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Test
    public void testGetAddressById() {
//        memberFeignClient.getAddressById()
    }

    @Test
    public void testSendCancelOrder() {
        log.info("关闭订单");
        Message message = MessageBuilder
                .withBody(JSON.toJSONBytes("123456"))
                .setHeader(MessageProperties.X_DELAY, OrderMqConstants.ORDER_CLOSE_DELAY_TIME)
                .build();
        rabbitTemplate.convertAndSend(OrderMqConstants.ORDER_EXCHANGE, OrderMqConstants.ORDER_CLOSE_ROUTING_KEY, message);
    }

    @Test
    public void testCloseOrder() {
        Order order = orderService.lambdaQuery().select(Order::getStatus).eq(Order::getOrderSn, "123456").one();
        System.out.println(order);
    }

    @Test
    public void testOrderList() {
        List<String> collect = orderService.lambdaQuery().eq(Order::getMemberId, 5L).select(Order::getOrderSn).list().stream().map(Order::getOrderSn).collect(Collectors.toList());
        System.out.println(collect);

        List<String> orderList = new ArrayList<>();
        orderList.add("202306081954276231666775695353282561");
        List<OrderItem> orderItemList = orderItemMapper.getOrderItemByOrderSns(orderList);
        System.out.println(orderItemList);
    }



}
