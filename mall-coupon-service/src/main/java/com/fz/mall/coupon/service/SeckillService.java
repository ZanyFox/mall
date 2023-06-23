package com.fz.mall.coupon.service;

import com.fz.mall.api.coupon.dto.SeckillSkuDTO;
import com.fz.mall.coupon.pojo.SeckillSkuVO;

import java.util.List;

public interface SeckillService {

    List<SeckillSkuVO> getSeckillSkus();

    SeckillSkuDTO getSeckillSkuInfoBySkuId(Long id);

    /**
     * 使用lua脚本秒杀
     * @param killId
     * @param code
     * @param count
     * @return
     */
    void seckill(String killId, String code, Integer count);
}
