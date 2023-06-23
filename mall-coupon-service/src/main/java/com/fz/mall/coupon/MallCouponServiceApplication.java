package com.fz.mall.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"com.fz.mall.api.**.feign"})
@SpringBootApplication(scanBasePackages = {"com.fz.mall"})
public class MallCouponServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallCouponServiceApplication.class, args);
    }
}
