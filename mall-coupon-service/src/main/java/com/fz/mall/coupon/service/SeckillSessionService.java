package com.fz.mall.coupon.service;

import com.fz.mall.coupon.entity.SeckillSession;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDateTime;

/**
 * <p>
 * 秒杀活动场次 服务类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
public interface SeckillSessionService extends IService<SeckillSession> {

    /**
     * 上架指定日期的秒杀商品
     */
    void uploadSeckillSku();


}
