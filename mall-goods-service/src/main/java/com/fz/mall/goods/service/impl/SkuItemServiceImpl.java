package com.fz.mall.goods.service.impl;

import com.fz.mall.api.coupon.dto.SeckillSkuDTO;
import com.fz.mall.api.coupon.feign.CouponFeignClient;
import com.fz.mall.common.resp.ServRespEntity;
import com.fz.mall.goods.pojo.entity.SkuImages;
import com.fz.mall.goods.pojo.entity.SkuInfo;
import com.fz.mall.goods.pojo.entity.SpuInfoDesc;
import com.fz.mall.goods.pojo.vo.SeckillSkuVO;
import com.fz.mall.goods.pojo.vo.SkuItemVO;
import com.fz.mall.goods.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Service
@AllArgsConstructor
public class SkuItemServiceImpl implements SkuItemService {

    private SkuInfoService skuInfoService;

    private SkuImagesService skuImagesService;

    private SpuInfoDescService spuInfoDescService;

    private AttrGroupService attrGroupService;

    private SkuSaleAttrValueService skuSaleAttrValueService;

    private ThreadPoolExecutor threadPoolExecutor;

    private CouponFeignClient couponFeignClient;

    @Override
    public SkuItemVO getSkuItemById(Long skuId) {

        SkuItemVO skuItemVO = new SkuItemVO();

        SkuInfo skuInfo = skuInfoService.getSkuBySkuId(skuId);
        skuItemVO.setInfo(skuInfo);

        if (skuInfo == null || skuInfo.getSkuId() == null)
            return skuItemVO;

        CompletableFuture<Void> skuImagesFuture = CompletableFuture.runAsync(() -> {
            List<SkuImages> skuImages = skuImagesService.getSkuImagesBySkuId(skuId);
            skuItemVO.setImages(skuImages);
        }, threadPoolExecutor);

        CompletableFuture<Void> spuInfoDescFuture = CompletableFuture.runAsync(() -> {
            SpuInfoDesc spuInfoDesc = spuInfoDescService.lambdaQuery().eq(SpuInfoDesc::getSpuId, skuInfo.getSpuId()).one();
            skuItemVO.setDesc(spuInfoDesc);
        }, threadPoolExecutor);


        CompletableFuture<Void> attrsFuture = CompletableFuture.runAsync(() -> {
            List<SkuItemVO.SkuSaleAttrVO> saleAttrs = skuSaleAttrValueService.getPurchaseAttrsBySpuId(skuInfo.getSpuId());
            skuItemVO.setSaleAttr(saleAttrs);
        }, threadPoolExecutor);


        CompletableFuture<Void> attrGroupFuture = CompletableFuture.runAsync(() -> {
            List<SkuItemVO.AttrGroupVO> attrGroupWithAllAttrs = attrGroupService.getSkuAttrGroupWithAllAttrs(skuInfo.getCategoryId(), skuInfo.getSkuId(), skuInfo.getSpuId());
            skuItemVO.setGroupAttrs(attrGroupWithAllAttrs);
        }, threadPoolExecutor);


        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        CompletableFuture<Void> seckillSkuFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            ServRespEntity<SeckillSkuDTO> seckillInfoResp = couponFeignClient.getSkuSeckillInfo(skuId);
            SeckillSkuDTO seckillSkuDTO = seckillInfoResp.getData();
            if (seckillSkuDTO != null) {
                SeckillSkuVO seckillSkuVO = new SeckillSkuVO();
                BeanUtils.copyProperties(seckillSkuDTO, seckillSkuVO);
                seckillSkuVO.setStartTime(seckillSkuDTO.getStartTime().toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
                seckillSkuVO.setEndTime(seckillSkuDTO.getEndTime().toInstant(ZoneOffset.ofHours(8)).toEpochMilli());
                skuItemVO.setSeckillSkuVo(seckillSkuVO);
                log.info("秒杀商品: {}", seckillSkuVO);
            }
        }, threadPoolExecutor);

        CompletableFuture.allOf(skuImagesFuture, spuInfoDescFuture, attrsFuture, attrGroupFuture, seckillSkuFuture).join();

        return skuItemVO;
    }
}
