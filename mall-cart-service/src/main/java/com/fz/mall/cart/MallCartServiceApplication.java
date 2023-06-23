package com.fz.mall.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"com.fz.mall.api.**.feign"})
@SpringBootApplication(scanBasePackages = {"com.fz.mall"})
public class MallCartServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(MallCartServiceApplication.class, args);
    }
}
