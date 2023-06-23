package com.fz.mall.order.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fz.mall.common.context.ContextHolder;
import com.fz.mall.common.exception.MallServerException;
import com.fz.mall.common.rabbitmq.dto.SeckillOrderDTO;
import com.fz.mall.common.redis.RedisCache;
import com.fz.mall.common.redis.constant.SeckillCacheConstants;
import com.fz.mall.common.redis.pojo.SeckillSkuCache;
import com.fz.mall.common.resp.ResponseEnum;
import com.fz.mall.order.constant.OrderConstants;
import com.fz.mall.order.constant.SeckillResult;
import com.fz.mall.order.pojo.vo.OrderInfoVO;
import com.fz.mall.order.pojo.vo.OrderItemInfoVO;
import com.fz.mall.order.service.OrderSeckillService;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class OrderSeckillServiceImpl implements OrderSeckillService {

    private RedisCache redisCache;

    private StringRedisTemplate redisTemplate;


    @Override
    public SeckillSkuCache seckill(Long sessionId, Long skuId, String code, Integer quantity) {

        Long uid = ContextHolder.getUser().getUid();
        SeckillSkuCache seckillSkuCache = redisCache.getHashObject(SeckillCacheConstants.SECKILL_SKU_PREFIX + sessionId, skuId.toString(), SeckillSkuCache.class);
        LocalDateTime current = LocalDateTime.now();
        if (!(current.isAfter(seckillSkuCache.getStartTime()) && current.isBefore(seckillSkuCache.getEndTime())))
            throw new MallServerException(ResponseEnum.SECKILL_TIME_ERROR);

        if (!(sessionId.equals(seckillSkuCache.getPromotionSessionId()) && skuId.equals(seckillSkuCache.getSkuId()) && code.equals(seckillSkuCache.getCode())))
            throw new MallServerException(ResponseEnum.SECKILL_VERIFY_ERROR);

        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setResultType(Long.class);
        script.setLocation(new ClassPathResource("lua/seckill.lua"));
        String stockKey = SeckillCacheConstants.SECKILL_SKU_STOCK_PREFIX + seckillSkuCache.getCode();
        String userPurchaseKey = SeckillCacheConstants.SECKILL_PURCHASE_COUNT_KEY + uid + "_" + seckillSkuCache.getPromotionSessionId() + "_" + seckillSkuCache.getSkuId();
        long expireTime = seckillSkuCache.getEndTime().toEpochSecond(ZoneOffset.ofHours(8)) - seckillSkuCache.getStartTime().toEpochSecond(ZoneOffset.ofHours(8));
        Long result = redisTemplate.execute(script, Arrays.asList(stockKey, userPurchaseKey), quantity.toString(), seckillSkuCache.getSeckillLimit().toString(), String.valueOf(expireTime));
        if (result == null) throw new MallServerException(ResponseEnum.SERVER_INTERNAL_ERROR);
        if (result.equals(SeckillResult.STOCK_LACKING.getCode()))
            throw new MallServerException(ResponseEnum.SKU_STOCK_LACKING);
        if (result.equals(SeckillResult.BEYOND_LIMIT.getCode()))
            throw new MallServerException(ResponseEnum.BEYOND_PURCHASE_LIMIT);

        return seckillSkuCache;
    }

    @Override
    public OrderInfoVO getSeckillOrderInfo(String killId, String code, Integer quantity) {


        String sessionId = killId.split("-")[0];
        String skuId = killId.split("-")[1];
        SeckillSkuCache seckillSkuCache = redisCache.getHashObject(SeckillCacheConstants.SECKILL_SKU_PREFIX + sessionId, skuId, SeckillSkuCache.class);
        LocalDateTime current = LocalDateTime.now();
        if (!(current.isAfter(seckillSkuCache.getStartTime()) && current.isBefore(seckillSkuCache.getEndTime())))
            throw new MallServerException(ResponseEnum.SECKILL_TIME_ERROR);

        if (!(killId.equals(seckillSkuCache.getPromotionSessionId() + "-" + seckillSkuCache.getSkuId()) && code.equals(seckillSkuCache.getCode())))
            throw new MallServerException(ResponseEnum.SECKILL_VERIFY_ERROR);

        OrderInfoVO orderInfoVO = new OrderInfoVO();

        OrderItemInfoVO orderItemInfoVO = new OrderItemInfoVO();
        orderItemInfoVO.setImage(seckillSkuCache.getSkuDefaultImg());
        orderItemInfoVO.setPrice(seckillSkuCache.getSeckillPrice());
        orderItemInfoVO.setTitle(seckillSkuCache.getSkuTitle());
        orderItemInfoVO.setWeight(new BigDecimal(0));
        orderItemInfoVO.setCount(quantity);


        orderInfoVO.setItems(Collections.singletonList(orderItemInfoVO));
        String orderToken = IdUtil.fastSimpleUUID();
        redisTemplate.opsForValue().set(OrderConstants.ORDER_TOKEN_KEY + ContextHolder.getUser().getUid(), orderToken);
        orderInfoVO.setOrderToken(orderToken);
        Map<Long, Boolean> hasStockMap = new HashMap<>();
        hasStockMap.put(seckillSkuCache.getSkuId(), true);
        orderInfoVO.setStocks(hasStockMap);
        orderInfoVO.setIntegration(0);
        orderInfoVO.setSeckill(true);
        orderInfoVO.setSeckillSkuId(seckillSkuCache.getSkuId());
        orderInfoVO.setSeckillQuantity(quantity);
        orderInfoVO.setSeckillSessionId(Long.parseLong(sessionId));
        orderInfoVO.setSeckillCode(seckillSkuCache.getCode());

        return orderInfoVO;
    }
}
