package com.fz.mall.coupon.mapper;

import com.fz.mall.coupon.entity.SeckillSession;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fz.mall.coupon.pojo.SeckillSessionDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 秒杀活动场次 Mapper 接口
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
public interface SeckillSessionMapper extends BaseMapper<SeckillSession> {

    List<SeckillSessionDTO> getSeckillSessionWithSkuByTime(@Param("start") String start, @Param("end") String end);
}
