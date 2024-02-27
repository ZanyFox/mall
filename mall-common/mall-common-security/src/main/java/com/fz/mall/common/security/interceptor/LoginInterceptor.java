package com.fz.mall.common.security.interceptor;

import com.alibaba.fastjson2.JSON;
import com.fz.mall.common.context.ContextHolder;
import com.fz.mall.common.context.UserContext;
import com.fz.mall.common.redis.constant.TokenCacheConstants;
import com.fz.mall.common.resp.ResponseEnum;
import com.fz.mall.common.resp.ServRespEntity;
import com.fz.mall.common.security.constant.SecurityConstants;
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


public class LoginInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);


    private final StringRedisTemplate redisTemplate;

    public LoginInterceptor(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        logger.info("TokenInterceptor  当前线程: {} 线程号: {} 请求路径: {} ", Thread.currentThread().getName(), Thread.currentThread().getId(), request.getRequestURI());


        Cookie[] cookies = request.getCookies();
        if (ObjectUtils.isEmpty(cookies)) {
            logger.info("没有 cookie  直接逮捕");
            writeUnauthorizedResp(response);
            return false;
        }

        String token = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(SecurityConstants.USER_ACCESS_TOKEN) && ObjectUtils.isNotEmpty(cookie.getValue())) {
                token = cookie.getValue();
            }
        }

        if (ObjectUtils.isEmpty(token)) {
            logger.info("没有user_access_token cookie  直接逮捕");
            writeUnauthorizedResp(response);
            return false;
        }

        String userStr = redisTemplate.opsForValue().get(TokenCacheConstants.ACCESS_TOKEN_KEY + token);
        UserContext userContext = JSON.parseObject(userStr, UserContext.class);

        if (userContext == null) {
            logger.info("没有用户信息  直接逮捕");
            writeUnauthorizedResp(response);
            return false;
        }

        ContextHolder.set(SecurityConstants.USER_CONTEXT, userContext);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        ContextHolder.clear();
    }

    private void writeUnauthorizedResp(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(JSON.toJSONString(ServRespEntity.fail(ResponseEnum.ACCESS_PERMISSION_ERROR)));
    }


}
