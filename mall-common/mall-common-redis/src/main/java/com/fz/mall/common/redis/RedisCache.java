package com.fz.mall.common.redis;

import com.alibaba.fastjson2.JSON;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class RedisCache {
    public RedisCache(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    private final StringRedisTemplate stringRedisTemplate;


    public <T> void putHashObject(String key, String hashKey, T value) {

        String jsonString = JSON.toJSONString(value);
        stringRedisTemplate.opsForHash().put(key, hashKey, jsonString);
    }

    public void putAllHashObject(String key, Map<?, ?> map) {

        Map<String, String> cacheHashMap = map.entrySet().stream()
                .collect(Collectors.toMap((entry) -> JSON.toJSONString(entry.getKey()), (entry) -> JSON.toJSONString(entry.getValue())));

        stringRedisTemplate.opsForHash().putAll(key, cacheHashMap);
    }

    public void putAllHashObject(String key, Map<?, ?> map, Duration duration) {

        Map<String, String> cacheHashMap = map.entrySet().stream()
                .collect(Collectors.toMap((entry) -> JSON.toJSONString(entry.getKey()), (entry) -> JSON.toJSONString(entry.getValue())));

        stringRedisTemplate.opsForHash().putAll(key, cacheHashMap);
        stringRedisTemplate.expire(key, duration);
    }

    public <T> T getHashObject(String key, String hashKey, Class<T> clazz) {
        String str = (String) stringRedisTemplate.opsForHash().get(key, hashKey);
        return JSON.parseObject(str, clazz);
    }

    public <T> List<T> getHashObjectList(String key, Class<T> clazz) {

        return stringRedisTemplate.opsForHash()
                .entries(key).values().stream()
                .map(value -> JSON.parseObject(value.toString(), clazz)).collect(Collectors.toList());
    }


    public <T> Map<String, T> getHashObjectMap(String key, Class<T> clazz) {

        return stringRedisTemplate.opsForHash()
                .entries(key).entrySet().stream()
                .collect(Collectors.toMap((entry) -> entry.getKey().toString(), (entry) -> JSON.parseObject(entry.getValue().toString(), clazz)));
    }

    public <T> void setObject(String key, T value) {

        String jsonString = JSON.toJSONString(value);
        stringRedisTemplate.opsForValue().set(key, jsonString);
    }

    public <T> void setObject(String key, T value, long timeout, TimeUnit timeUnit) {

        String jsonString = JSON.toJSONString(value);
        stringRedisTemplate.opsForValue().set(key, jsonString);
    }

    public <T> T getObject(String key, Class<T> clazz) {
        String str = stringRedisTemplate.opsForValue().get(key);
        return JSON.parseObject(str, clazz);
    }


}
