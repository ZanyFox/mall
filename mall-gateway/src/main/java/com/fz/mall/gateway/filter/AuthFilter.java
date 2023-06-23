package com.fz.mall.gateway.filter;

import com.alibaba.fastjson2.JSON;
import com.fz.mall.common.resp.ServerResponseEntity;
import com.fz.mall.common.resp.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {


    public static final String USER_ACCESS_TOKEN = "user_access_token";

    public static final String ACCESS_TOKEN_KEY = "access_token:";

    public AuthFilter(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private final RedisTemplate<String, Object> redisTemplate;

    private final List<String> paths = new ArrayList<>();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        log.info("当前线程: {} 请求URI: {}", Thread.currentThread().getName(), request.getURI());
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();

        InetSocketAddress host = request.getHeaders().getHost();

        if (host == null || ObjectUtils.isEmpty(host.getHostName())) {
            log.info("没有HOST 直接逮捕  " + request.getURI());
            return webFluxUnauthorizedResponse(exchange);
        }

        AntPathMatcher matcher = new AntPathMatcher();
        boolean isMissing = paths.stream().noneMatch(s -> matcher.match(s, request.getURI().toString()));
        if (isMissing) return chain.filter(exchange);


        HttpCookie tokenCookie = cookies.getFirst(USER_ACCESS_TOKEN);
        if (tokenCookie == null || ObjectUtils.isEmpty(tokenCookie.getValue())) {
            log.info("没有user_access_token cookie 直接逮捕  " + request.getURI());
            return webFluxUnauthorizedResponse(exchange);
        }
        String token = tokenCookie.getValue();
        Boolean isAuth = redisTemplate.hasKey(ACCESS_TOKEN_KEY + token);
        if (!BooleanUtils.isTrue(isAuth)) {
            log.info("没有用户  直接逮捕  " + request.getURI());
            return webFluxUnauthorizedResponse(exchange);
        }


        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }


    private Mono<Void> webFluxUnauthorizedResponse(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        DataBuffer dataBuffer = response.bufferFactory().wrap(JSON.toJSONBytes(ServerResponseEntity.fail(ResponseEnum.ACCESS_PERMISSION_ERROR)));
        return response.writeWith(Mono.just(dataBuffer));
    }
}
