package com.fz.mall.goods.controller.feign;


import com.fz.mall.api.goods.dto.CartSkuInfoDTO;
import com.fz.mall.api.goods.dto.OrderSpuInfoDTO;
import com.fz.mall.api.goods.dto.SkuInfoDTO;
import com.fz.mall.api.goods.feign.GoodsFeignClient;
import com.fz.mall.common.data.bo.EsSkuBO;
import com.fz.mall.common.resp.ResponseEnum;
import com.fz.mall.common.resp.ServerResponseEntity;
import com.fz.mall.goods.pojo.entity.SkuInfo;
import com.fz.mall.goods.service.SkuInfoService;
import com.fz.mall.goods.service.SkuSaleAttrValueService;
import com.fz.mall.goods.service.SpuInfoService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class GoodsFeignController implements GoodsFeignClient {

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private SpuInfoService spuInfoService;

    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    @Override
    public ServerResponseEntity<SkuInfoDTO> getSkuInfoById(Long skuId) {
        SkuInfo skuInfo = skuInfoService.getById(skuId);
        SkuInfoDTO skuInfoDTO = new SkuInfoDTO();
        if (skuInfo != null)
            BeanUtils.copyProperties(skuInfo, skuInfoDTO);
        return ServerResponseEntity.success(skuInfoDTO);
    }

    @Override
    public ServerResponseEntity<List<SkuInfoDTO>> getSkuInfoByIds(List<Long> skuIds) {

        List<SkuInfoDTO> skuInfoDTOS = new ArrayList<>();
        if (ObjectUtils.isNotEmpty(skuIds))
            skuInfoDTOS = skuInfoService.lambdaQuery()
                    .in(SkuInfo::getSkuId, skuIds).list().stream().map((item) -> {
                        SkuInfoDTO skuInfoDTO = new SkuInfoDTO();
                        BeanUtils.copyProperties(item, skuInfoDTO);
                        return skuInfoDTO;
                    }).collect(Collectors.toList());

        return ServerResponseEntity.success(skuInfoDTOS);
    }

    @Override
    public ServerResponseEntity<List<EsSkuBO>> getEsSkuBOsBySpuId(Long spuId) {
        return ServerResponseEntity.success(spuInfoService.getEsSkuBOsBySpuId(spuId));
    }

    @Override
    public ServerResponseEntity<List<String>> getSkuSaleAttrs(Long skuId) {
        List<String> attrs = skuSaleAttrValueService.getSaleAttrListBySkuId(skuId);
        return ServerResponseEntity.success(attrs);
    }

    @Override
    public ServerResponseEntity<Map<Long, List<String>>> getSkuSaleAttrsBySkuIds(List<Long> skuIds) {
        Map<Long, List<String>> skuIdSaleAttrValuesMap = skuSaleAttrValueService.getSaleAttrValuesBySkuIds(skuIds);
        return ServerResponseEntity.success(skuIdSaleAttrValuesMap);
    }

    @Override
    public ServerResponseEntity<List<CartSkuInfoDTO>> getCartSkuInfosByIds(List<Long> skuIds) {

        List<CartSkuInfoDTO> cartSkuInfoDTOS = skuInfoService.getCartSkuById(skuIds);
        return ServerResponseEntity.success(cartSkuInfoDTOS);
    }

    @Override
    public ServerResponseEntity<List<OrderSpuInfoDTO>> getOrderSpuInfoBySkuIds(List<Long> skuIds) {

        return null;
    }


}
