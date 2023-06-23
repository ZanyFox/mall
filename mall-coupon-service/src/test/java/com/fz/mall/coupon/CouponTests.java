package com.fz.mall.coupon;

import com.fz.mall.api.coupon.dto.SeckillSkuDTO;
import com.fz.mall.common.config.JacksonConfiguration;
import com.fz.mall.common.redis.constant.SeckillCacheConstants;
import com.fz.mall.coupon.mapper.SeckillSessionMapper;
import com.fz.mall.coupon.pojo.SeckillSessionDTO;
import com.fz.mall.coupon.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class CouponTests {

    @Autowired
    private SeckillSessionMapper seckillSessionMapper;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SeckillService seckillService;


    @Test
    public void testGetSeckillSessionWithSkuByTime() {
        String startTime = DateTimeFormatter.ofPattern(JacksonConfiguration.DEFAULT_DATE_TIME_FORMAT).format(LocalDateTime.now());
        String endTime = DateTimeFormatter.ofPattern(JacksonConfiguration.DEFAULT_DATE_TIME_FORMAT).format(LocalDateTime.now().plusDays(3));

        List<SeckillSessionDTO> seckillSessionWithSku = seckillSessionMapper.getSeckillSessionWithSkuByTime(startTime, endTime);
        System.out.println(seckillSessionWithSku);
    }

    @Test
    public void testRedisson() {
        RLock lock = redissonClient.getLock("lock");
        try {
            boolean isLocked = lock.tryLock();
            if (isLocked) log.info("获取锁成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    @Test
    public void testGetSeckillSkuInfoBySkuId() {

        SeckillSkuDTO seckillSkuDTO = seckillService.getSeckillSkuInfoBySkuId(32L);
        System.out.println(seckillSkuDTO);
    }

    @Test
    public void testSeckill() {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setResultType(Long.class);
        script.setLocation(new ClassPathResource("lua/seckill.lua"));
        String stockKey = "stock:1";
        String userPurchaseKey = "user:purchase:10_1";
//        Long result = redisTemplate.execute(script, Arrays.asList(stockKey, userPurchaseKey), "10", "20");

        DefaultRedisScript<Long> ORDER_TOKEN_SCRIPT = new DefaultRedisScript<>("return tonumber(redis.call('get', KEYS[1])) or 0", Long.class);

        String userPurchaseKey1 =  5 + "_" + 1 + "_" + 32;

        Long result = redisTemplate.execute(ORDER_TOKEN_SCRIPT, Collections.singletonList(userPurchaseKey1));
        System.out.println(result);
    }

}
