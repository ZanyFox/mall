package com.fz.mall.common.cache.config;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.ReflectionUtils;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@EnableCaching
@Configuration
public class SpringCacheRedisConfig {


    private static final Long REDIS_CACHE_DEFAULT_TTL = 3600L;



    /**
     * redis缓存默认配置类
     *
     * @param cacheProperties 缓存相关的配置
     * @return
     */
    @Bean
    public RedisCacheConfiguration defaultRedisCacheConfiguration(CacheProperties cacheProperties, RedisSerializer<?> redisSerializer) {

        CacheProperties.Redis redisProperties = cacheProperties.getRedis();

        RedisCacheConfiguration config = RedisCacheConfiguration
                .defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .entryTtl(Duration.ofSeconds(REDIS_CACHE_DEFAULT_TTL));

        return withCacheProperties(config, redisProperties);
    }


    private RedisCacheConfiguration withCacheProperties(RedisCacheConfiguration config, CacheProperties.Redis redisProperties) {
        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (redisProperties.getKeyPrefix() != null) {
            config = config.prefixCacheNameWith(redisProperties.getKeyPrefix());
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }
        //覆盖默认key双冒号  CacheKeyPrefix#prefixed
        config = config.computePrefixWith(name -> name);
        return config;
    }

    /**
     * 对RedisCacheManager添加一些自定义的功能
     */
    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer(ApplicationContext applicationContext, List<CacheTtlAdapter> cacheTtlAdapters, CacheProperties cacheProperties) {

        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        return builder -> builder
                //事务敏感缓存 如果事务没有激活，就和正常的Cache功能一样，但激活后，put/evict/clear缓存操作将在事务成功提交后执行
                .transactionAware()
                // 根据key使用对应的配置文件
                .withInitialCacheConfigurations(buildInitialCacheConfigurations(applicationContext, cacheTtlAdapters, redisProperties));
    }


    /**
     * 名称对应的配置文件 自定义TTL 自定义序列化器  缓存不携带@type信息
     *
     * @param applicationContext 容器
     * @param cacheTtlAdapters   名称对应的TTL
     * @param redisProperties    配置文件
     * @return
     */
    private Map<String, RedisCacheConfiguration> buildInitialCacheConfigurations(ApplicationContext applicationContext, List<CacheTtlAdapter> cacheTtlAdapters, CacheProperties.Redis redisProperties) {
        Map<String, Duration> cacheNameTtlMap;
        if (cacheTtlAdapters != null) {
            List<TtlCacheName> ttlCacheNames = new ArrayList<>();
            cacheTtlAdapters.forEach(item -> ttlCacheNames.addAll(item.listCacheNameWithTtl()));
            cacheNameTtlMap = ttlCacheNames.stream().collect(Collectors.toMap(TtlCacheName::getCacheName, TtlCacheName::getTtl));
        } else {
            cacheNameTtlMap = new HashMap<>();
        }
        HashMap<String, RedisCacheConfiguration> cacheConfigMap = new HashMap<>();
        Arrays.stream(applicationContext.getBeanNamesForType(Object.class))
                .map(applicationContext::getType).filter(Objects::nonNull)
                .forEach(clazz -> ReflectionUtils.doWithMethods(clazz, method -> {
                            ReflectionUtils.makeAccessible(method);
                            Cacheable cacheable = AnnotationUtils.findAnnotation(method, Cacheable.class);
                            if (Objects.nonNull(cacheable)) {
                                for (String cacheName : cacheable.cacheNames()) {
                                    RedisSerializationContext.SerializationPair<Object> sp = RedisSerializationContext.SerializationPair
                                            .fromSerializer(new JacksonRedisSerializer<>(method.getGenericReturnType()));
                                    RedisCacheConfiguration configuration = RedisCacheConfiguration
                                            .defaultCacheConfig()
                                            .serializeValuesWith(sp);
                                    configuration = withCacheProperties(configuration, redisProperties);
                                    Duration duration = redisProperties.getTimeToLive() == null ? Duration.ofSeconds(REDIS_CACHE_DEFAULT_TTL) : redisProperties.getTimeToLive();
                                    configuration = configuration.entryTtl(cacheNameTtlMap.getOrDefault(cacheName, duration));
                                    cacheConfigMap.put(cacheName, configuration);
                                }
                            }
                        })
                );
        return cacheConfigMap;
    }
}
