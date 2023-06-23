package com.fz.mall.biz;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = {"com.fz.mall.api.**.feign"})
@SpringBootApplication(scanBasePackages = "com.fz.mall")
public class MallBizServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallBizServiceApplication.class);
    }
}
