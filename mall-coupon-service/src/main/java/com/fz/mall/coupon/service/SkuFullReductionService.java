package com.fz.mall.coupon.service;

import com.fz.mall.api.coupon.dto.SkuReductionDTO;
import com.fz.mall.coupon.entity.SkuFullReduction;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商品满减信息 服务类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
public interface SkuFullReductionService extends IService<SkuFullReduction> {

    void save(SkuReductionDTO skuReductionDTO);
}
