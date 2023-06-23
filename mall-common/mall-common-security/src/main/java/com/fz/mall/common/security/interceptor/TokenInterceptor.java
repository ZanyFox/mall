package com.fz.mall.common.security.interceptor;

import com.alibaba.fastjson2.JSON;
import com.fz.mall.common.security.constant.SecurityConstants;
import com.fz.mall.common.context.UserContext;
import com.fz.mall.common.context.ContextHolder;
import com.fz.mall.common.redis.constant.TokenCacheConstants;
import com.fz.mall.common.resp.ServerResponseEntity;
import com.fz.mall.common.resp.ResponseEnum;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;



public class TokenInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);


    private final StringRedisTemplate redisTemplate;

    public TokenInterceptor(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        logger.info("TokenInterceptor  当前线程: {} 线程号: {} 请求路径: {} ", Thread.currentThread().getName(), Thread.currentThread().getId(), request.getRequestURI());


        Cookie[] cookies = request.getCookies();
        if (ObjectUtils.isEmpty(cookies)) {
            return true;
        }

        String token = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(SecurityConstants.USER_ACCESS_TOKEN) && ObjectUtils.isNotEmpty(cookie.getValue())) {
                token = cookie.getValue();
            }
        }
        String userStr = redisTemplate.opsForValue().get(TokenCacheConstants.ACCESS_TOKEN_KEY + token);
        UserContext userContext = JSON.parseObject(userStr, UserContext.class);
        ContextHolder.set(SecurityConstants.USER_CONTEXT, userContext);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        ContextHolder.clear();
    }



}
