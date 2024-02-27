package com.fz.mall.coupon.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fz.mall.api.goods.dto.SkuInfoDTO;
import com.fz.mall.api.goods.feign.GoodsFeignClient;
import com.fz.mall.common.config.JacksonConfiguration;
import com.fz.mall.common.exception.MallServerException;
import com.fz.mall.common.redis.RedisCache;
import com.fz.mall.common.redis.constant.SeckillCacheConstants;
import com.fz.mall.common.redis.pojo.SeckillSkuCache;
import com.fz.mall.common.resp.ResponseEnum;
import com.fz.mall.common.resp.ServRespEntity;
import com.fz.mall.coupon.entity.SeckillSession;
import com.fz.mall.coupon.entity.SeckillSkuRelation;
import com.fz.mall.coupon.mapper.SeckillSessionMapper;
import com.fz.mall.coupon.pojo.SeckillSessionDTO;
import com.fz.mall.coupon.service.SeckillSessionService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 秒杀活动场次 服务实现类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@Service
@AllArgsConstructor
public class SeckillSessionServiceImpl extends ServiceImpl<SeckillSessionMapper, SeckillSession> implements SeckillSessionService {


    private final SeckillSessionMapper seckillSessionMapper;

    private final GoodsFeignClient goodsFeignClient;

    private final StringRedisTemplate redisTemplate;

    private final RedisCache redisCache;


    @Override
    public void uploadSeckillSku() {

        String startTime = DateTimeFormatter.ofPattern(JacksonConfiguration.DEFAULT_DATE_TIME_FORMAT).format(LocalDateTime.now().with(LocalTime.MIN));
        String endTime = DateTimeFormatter.ofPattern(JacksonConfiguration.DEFAULT_DATE_TIME_FORMAT).format(LocalDateTime.now().plusDays(3));

        List<SeckillSessionDTO> seckillSessionWithSku = seckillSessionMapper.getSeckillSessionWithSkuByTime(startTime, endTime);
        List<Long> relationSkuIds = seckillSessionWithSku.stream()
                .flatMap((Function<SeckillSessionDTO, Stream<Long>>) seckillSessionDTO -> seckillSessionDTO.getRelations().stream().map(SeckillSkuRelation::getSkuId)).collect(Collectors.toList());

        if (ObjectUtils.isEmpty(relationSkuIds))
            throw new MallServerException(ResponseEnum.SECKILL_SKU_NOT_EXIST_ERROR);

        ServRespEntity<List<SkuInfoDTO>> skuInfosResp = goodsFeignClient.getSkuInfoByIds(relationSkuIds);
        List<SkuInfoDTO> skuInfos = skuInfosResp.getData();

        Map<Long, SkuInfoDTO> skuInfoMap = skuInfos.stream().collect(Collectors.toMap(SkuInfoDTO::getSkuId, (item) -> item));

        for (SeckillSessionDTO seckillSessionDTO : seckillSessionWithSku) {

            String key = SeckillCacheConstants.SECKILL_SESSION_PREFIX
                    + seckillSessionDTO.getStartTime().toEpochSecond(ZoneOffset.ofHours(8)) + "_"
                    + seckillSessionDTO.getEndTime().toEpochSecond(ZoneOffset.ofHours(8)) + "_" + seckillSessionDTO.getId();

            List<String> skuIds = seckillSessionDTO.getRelations().stream().map((item) -> item.getSkuId().toString()).collect(Collectors.toList());
            redisTemplate.opsForList().leftPushAll(key, skuIds);
            redisTemplate.expire(key, Duration.ofHours(2));

            Map<Long, SeckillSkuCache> seckillSkuCacheMap = seckillSessionDTO.getRelations().stream().map((relation) -> {
                SeckillSkuCache seckillSkuCache = new SeckillSkuCache();
                BeanUtils.copyProperties(relation, seckillSkuCache);
                BeanUtils.copyProperties(skuInfoMap.get(relation.getSkuId()), seckillSkuCache);
                String seckillCode = IdUtil.fastSimpleUUID();
                seckillSkuCache.setCode(seckillCode);
                seckillSkuCache.setStartTime(seckillSessionDTO.getStartTime());
                seckillSkuCache.setEndTime(seckillSessionDTO.getEndTime());
                // 保存商品库存
                redisTemplate.opsForValue().set(SeckillCacheConstants.SECKILL_SKU_STOCK_PREFIX + seckillCode, relation.getSeckillCount().toString(), Duration.ofHours(2));
                return seckillSkuCache;
            }).collect(Collectors.toMap(SeckillSkuCache::getSkuId, item -> item));

            redisCache.putAllHashObject(SeckillCacheConstants.SECKILL_SKU_PREFIX + seckillSessionDTO.getId(), seckillSkuCacheMap, Duration.ofHours(2));

        }
    }
}
