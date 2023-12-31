package com.fz.mall.api.coupon.feign;

import com.fz.mall.api.coupon.dto.SeckillSkuDTO;
import com.fz.mall.api.coupon.dto.SkuReductionDTO;
import com.fz.mall.api.coupon.dto.SpuBoundDTO;
import com.fz.mall.common.feign.FeignInsideProperties;
import com.fz.mall.common.resp.ServerResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "mall-coupon-service", contextId = "coupon")
public interface CouponFeignClient {


    @PostMapping(FeignInsideProperties.FEIGN_PREFIX + "/coupon/spu-bounds/save")
    ServerResponseEntity saveSpuBounds(@RequestBody SpuBoundDTO spuBoundDTO);

    @PostMapping(FeignInsideProperties.FEIGN_PREFIX + "/coupon/sku-full-reduction")
    ServerResponseEntity saveSkuReduction(@RequestBody SkuReductionDTO skuReductionDTO);

    @GetMapping(FeignInsideProperties.FEIGN_PREFIX + "/coupon/seckill/{skuId}")
    ServerResponseEntity<SeckillSkuDTO> getSkuSeckillInfo(@PathVariable("skuId") Long id);

}
