package com.fz.mall.coupon.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fz.mall.api.coupon.dto.SeckillSkuDTO;
import com.fz.mall.common.context.ContextHolder;
import com.fz.mall.common.exception.MallServerException;
import com.fz.mall.common.rabbitmq.constant.OrderMqConstants;
import com.fz.mall.common.rabbitmq.dto.SeckillOrderDTO;
import com.fz.mall.common.redis.RedisCache;
import com.fz.mall.common.redis.constant.SeckillCacheConstants;
import com.fz.mall.common.redis.pojo.SeckillSkuCache;
import com.fz.mall.common.resp.ResponseEnum;
import com.fz.mall.coupon.constant.SeckillResult;
import com.fz.mall.coupon.pojo.SeckillSkuVO;
import com.fz.mall.coupon.service.SeckillService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SeckillServiceImpl implements SeckillService {


    private final StringRedisTemplate redisTemplate;

    private final RedisCache redisCache;

    private final RabbitTemplate rabbitTemplate;

    @Override
    public List<SeckillSkuVO> getSeckillSkus() {

        long currentTime = Instant.now().getEpochSecond();
        Set<String> keys = redisTemplate.keys(SeckillCacheConstants.SECKILL_SESSION_PREFIX + "*");
        if (ObjectUtils.isEmpty(keys)) return null;
        List<String> filterKeys = keys.stream().filter((key) -> {
            String[] keySplit = key.substring(key.lastIndexOf(":") + 1).split("_");
            return currentTime >= Long.parseLong(keySplit[0]) && currentTime <= Long.parseLong(keySplit[1]);
        }).collect(Collectors.toList());
        if (ObjectUtils.isEmpty(filterKeys)) return null;
        String sessionId = filterKeys.get(0).split("_")[2];
        return redisCache.getHashObjectList(SeckillCacheConstants.SECKILL_SKU_PREFIX + sessionId, SeckillSkuVO.class);
    }

    @Override
    public SeckillSkuDTO getSeckillSkuInfoBySkuId(Long id) {

        long currentTime = Instant.now().getEpochSecond();
        Set<String> keys = redisTemplate.keys(SeckillCacheConstants.SECKILL_SESSION_PREFIX + "*");
        if (ObjectUtils.isEmpty(keys)) return null;
        List<String> sortedKeys = keys.stream().sorted(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                long startTime1 = Long.parseLong(o1.substring(o1.lastIndexOf(":") + 1).split("_")[0]);
                long startTime2 = Long.parseLong(o2.substring(o2.lastIndexOf(":") + 1).split("_")[0]);
                return (int) (startTime1 - startTime2);
            }
        }).collect(Collectors.toList());

        SeckillSkuDTO seckillSkuDTO = null;
        for (String key : sortedKeys) {
            Optional<String> skuIdOptional = Optional.ofNullable(redisTemplate.opsForList().range(key, 0, -1)).orElse(Collections.emptyList())
                    .stream().filter((e) -> Long.valueOf(e).equals(id)).findFirst();
            if (skuIdOptional.isPresent()) {
                String[] keySplit = key.substring(key.lastIndexOf(":") + 1).split("_");
                String sessionId = keySplit[2];
                seckillSkuDTO = redisCache.getHashObject(SeckillCacheConstants.SECKILL_SKU_PREFIX + sessionId, id.toString(), SeckillSkuDTO.class);
                seckillSkuDTO.setStartTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(keySplit[0])), ZoneId.from(ZoneOffset.ofHours(8))));
                seckillSkuDTO.setEndTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(Long.parseLong(keySplit[1])), ZoneId.from(ZoneOffset.ofHours(8))));
                if (!(currentTime >= Long.parseLong(keySplit[0]) && currentTime <= Long.parseLong(keySplit[1]))) {
                    seckillSkuDTO.setCode(null);
                }
                break;
            }
        }
        return seckillSkuDTO;
    }

    @Override
    public void seckill(String killId, String code, Integer count) {

        Long uid = ContextHolder.getUser().getUid();
        String sessionId = killId.split("-")[0];
        String skuId = killId.split("-")[1];
        SeckillSkuCache seckillSkuCache = redisCache.getHashObject(SeckillCacheConstants.SECKILL_SKU_PREFIX + sessionId, skuId, SeckillSkuCache.class);
        LocalDateTime current = LocalDateTime.now();
        if (!(current.isAfter(seckillSkuCache.getStartTime()) && current.isBefore(seckillSkuCache.getEndTime())))
            throw new MallServerException(ResponseEnum.SECKILL_TIME_ERROR);

        if (!(killId.equals(seckillSkuCache.getPromotionSessionId() + "-" + seckillSkuCache.getSkuId()) && code.equals(seckillSkuCache.getCode())))
            throw new MallServerException(ResponseEnum.SECKILL_VERIFY_ERROR);

        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setResultType(Long.class);
        script.setLocation(new ClassPathResource("lua/seckill.lua"));
        String stockKey = SeckillCacheConstants.SECKILL_SKU_STOCK_PREFIX + seckillSkuCache.getCode();
        String userPurchaseKey = SeckillCacheConstants.SECKILL_PURCHASE_COUNT_KEY + uid + "_" + seckillSkuCache.getPromotionSessionId() + "_" +seckillSkuCache.getSkuId();
        long expireTime = seckillSkuCache.getEndTime().toEpochSecond(ZoneOffset.ofHours(8)) - seckillSkuCache.getStartTime().toEpochSecond(ZoneOffset.ofHours(8));
        Long result = redisTemplate.execute(script, Arrays.asList(stockKey, userPurchaseKey), count.toString(), seckillSkuCache.getSeckillLimit().toString(), String.valueOf(expireTime));
        if (result == null) throw new MallServerException(ResponseEnum.SERVER_INTERNAL_ERROR);
        if (result.equals(SeckillResult.STOCK_LACKING.getCode()))
            throw new MallServerException(ResponseEnum.SKU_STOCK_LACKING);
        if (result.equals(SeckillResult.BEYOND_LIMIT.getCode()))
            throw new MallServerException(ResponseEnum.BEYOND_PURCHASE_LIMIT);

        SeckillOrderDTO seckillOrderDTO = new SeckillOrderDTO();
        seckillOrderDTO.setOrderSn(IdWorker.getTimeId());
        seckillOrderDTO.setUserId(uid);
        seckillOrderDTO.setCount(count);
        seckillOrderDTO.setPromotionSessionId(seckillSkuCache.getPromotionSessionId());
        seckillOrderDTO.setSkuId(seckillSkuCache.getSkuId());
        seckillOrderDTO.setSeckillPrice(seckillSkuCache.getSeckillPrice());
        seckillOrderDTO.setPrice(seckillSkuCache.getPrice());

//        rabbitTemplate.convertAndSend(OrderMqConstants.ORDER_EXCHANGE, OrderMqConstants.ORDER_SECKILL_CREATE_ROUTING_KEY, seckillOrderDTO);
    }
}
