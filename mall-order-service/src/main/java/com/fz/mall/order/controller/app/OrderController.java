package com.fz.mall.order.controller;

import com.fz.mall.api.user.feign.MemberFeignClient;
import com.fz.mall.common.resp.ServRespEntity;
import com.fz.mall.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberFeignClient memberFeignClient;

    @GetMapping("/list")
    public ServRespEntity list() {

//        ServerResponseEntity result = memberFeignClient.list();
//        log.info("用户信息: {}", result.toString());
        return ServRespEntity.success();
    }

}
