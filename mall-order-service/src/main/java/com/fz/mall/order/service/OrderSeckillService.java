package com.fz.mall.order.service;

import com.fz.mall.common.redis.pojo.SeckillSkuCache;
import com.fz.mall.order.pojo.vo.OrderInfoVO;

public interface OrderSeckillService {

    SeckillSkuCache seckill(Long sessionId, Long skuId, String code, Integer quantity);

    OrderInfoVO getSeckillOrderInfo(String killId, String code, Integer quantity);

}
