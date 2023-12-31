package com.fz.mall.coupon.controller.feign;


import com.fz.mall.api.coupon.dto.SeckillSkuDTO;
import com.fz.mall.api.coupon.dto.SkuReductionDTO;
import com.fz.mall.api.coupon.dto.SpuBoundDTO;
import com.fz.mall.api.coupon.feign.CouponFeignClient;
import com.fz.mall.common.resp.ServerResponseEntity;
import com.fz.mall.coupon.entity.SpuBounds;
import com.fz.mall.coupon.service.SeckillService;
import com.fz.mall.coupon.service.SkuFullReductionService;
import com.fz.mall.coupon.service.SpuBoundsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CouponFeignController implements CouponFeignClient {

    private SpuBoundsService spuBoundsService;

    private SkuFullReductionService skuFullReductionService;

    private SeckillService seckillService;

    @Override
    public ServerResponseEntity saveSpuBounds(@RequestBody SpuBoundDTO spuBoundDTO) {
        SpuBounds spuBounds = new SpuBounds();
        if ((spuBounds.getBuyBounds() != null && spuBounds.getBuyBounds().floatValue() != 0F) || (spuBounds.getGrowBounds() != null &&
                spuBounds.getGrowBounds().floatValue() != 0F)) {
            BeanUtils.copyProperties(spuBoundDTO, spuBounds);
            spuBoundsService.save(spuBounds);
        }
        return ServerResponseEntity.success();
    }

    @Override
    public ServerResponseEntity saveSkuReduction(SkuReductionDTO skuReductionDTO) {
        skuFullReductionService.save(skuReductionDTO);
        return ServerResponseEntity.success();
    }

    @Override
    public ServerResponseEntity<SeckillSkuDTO> getSkuSeckillInfo(Long id) {
        SeckillSkuDTO seckillSkuDTO = seckillService.getSeckillSkuInfoBySkuId(id);
        return ServerResponseEntity.success(seckillSkuDTO);
    }
}
