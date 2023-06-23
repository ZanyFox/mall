package com.fz.mall.common.security.config;

import com.fz.mall.common.security.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class WebSecurityMvcConfig implements WebMvcConfigurer {

    private final StringRedisTemplate stringRedisTemplate;

    private final  List<AuthPathAdapter> authPathAdapters;


    public WebSecurityMvcConfig(StringRedisTemplate stringRedisTemplate, List<AuthPathAdapter> authPathAdapters) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.authPathAdapters = authPathAdapters;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        List<String> excludePaths = authPathAdapters.stream()
                .flatMap((Function<AuthPathAdapter, Stream<String>>) authPathAdapter -> authPathAdapter.excludePathPattern().stream())
                .collect(Collectors.toList());

        registry.addInterceptor(new TokenInterceptor(stringRedisTemplate))
                .addPathPatterns("/**")
                .excludePathPatterns(excludePaths);
    }
}
