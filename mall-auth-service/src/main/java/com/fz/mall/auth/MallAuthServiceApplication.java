package com.fz.mall.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@EnableFeignClients(basePackages = {"com.fz.mall.api.**.feign"})
@SpringBootApplication(scanBasePackages = {"com.fz.mall"})
public class MallAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallAuthServiceApplication.class, args);
    }
}
