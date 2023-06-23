package com.fz.mall.common.session.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.session.DefaultCookieSerializerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * @author jitendra on 3/3/16.
 */
@Configuration
public class SpringSessionConfig {


    /**
     * 指定SpringSession使用Redis时的序列化器 需要将Bean名称指定为springSessionDefaultRedisSerializer
     *
     * @return
     */
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {

        return new GenericJackson2JsonRedisSerializer(new ObjectMapper());
    }


    @Bean
    public DefaultCookieSerializerCustomizer cookieSerializerCustomizer() {
        return cookieSerializer -> {
            cookieSerializer.setCookieName("mall_sid");
            // 放大域名 使该域名下的子域名都能共享cookie
            cookieSerializer.setDomainName("mallmall.com");
            cookieSerializer.setCookieMaxAge(30 * 24 * 3600);
        };
    }

}
