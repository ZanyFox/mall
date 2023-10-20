package com.fz.mall.common.cache.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CaffeineConfig {

    @Bean
    public Cache caffeineCache() {

        return Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(10))
                .build();
    }
}
