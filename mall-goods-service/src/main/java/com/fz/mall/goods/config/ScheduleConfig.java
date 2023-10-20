package com.fz.mall.goods.config;

import com.alibaba.fastjson2.JSON;
import com.fz.mall.common.redis.constant.GoodsCacheConstant;
import com.fz.mall.goods.pojo.entity.Category;
import com.fz.mall.goods.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;


@Configuration
@EnableScheduling
public class ScheduleConfig {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Scheduled(cron = "* */5 * * * ?")
    public void updateCategoryCacheFromDb() {

        List<Category> categories = categoryService.lambdaQuery().eq(Category::getCatLevel, 1).list();
        redisTemplate.opsForValue().set(GoodsCacheConstant.GOODS_CATEGORY_MENU, JSON.toJSONString(categories));
    }
}
