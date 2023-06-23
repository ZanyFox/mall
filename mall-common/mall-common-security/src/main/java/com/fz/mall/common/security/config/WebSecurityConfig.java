package com.fz.mall.common.security.config;

import com.fz.mall.common.security.filter.UserLoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.DispatcherType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class WebSecurityConfig {


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public FilterRegistrationBean<UserLoginFilter> userLoginFilterFilterRegistrationBean(UserLoginFilter userLoginFilter) {

        FilterRegistrationBean<UserLoginFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(userLoginFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        registrationBean.setOrder(0);
        return registrationBean;
    }

    @Bean
    public AuthPathAdapter authPathAdapter() {
        return () -> Arrays.asList(
                "/api/**", "/login.html", "/login", "/feign/user/login", "/feign/**", "/error"
        );
    }
}
