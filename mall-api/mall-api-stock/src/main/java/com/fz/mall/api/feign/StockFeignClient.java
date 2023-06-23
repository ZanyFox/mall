package com.fz.mall.api.feign;


import com.fz.mall.api.dto.FareDTO;
import com.fz.mall.api.dto.LockStockDTO;
import com.fz.mall.api.dto.SkuQuantityDTO;
import com.fz.mall.common.feign.FeignInsideProperties;
import com.fz.mall.common.resp.ServerResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "mall-stock-service")
public interface StockFeignClient {

    @GetMapping(FeignInsideProperties.FEIGN_PREFIX + "/stock/getHasStockBySkuIds")
    ServerResponseEntity<Map<Long, Boolean>> getSkuHasStockBySkuIds(@RequestBody List<Long> skuIds);

    @GetMapping(FeignInsideProperties.FEIGN_PREFIX + "/stock/getStockBySkuIds")
    ServerResponseEntity<Map<Long, Integer>> getSkuStockBySkuIds(@RequestBody List<Long> skuIds);

    @GetMapping(FeignInsideProperties.FEIGN_PREFIX + "/stock/getWareHasStock")
    ServerResponseEntity<Map<Long, List<Long>>> getWareHasStockBySkuQuantity(@RequestBody List<SkuQuantityDTO> skuQuantityDTOS);

    @GetMapping(FeignInsideProperties.FEIGN_PREFIX + "/stock/fare")
    ServerResponseEntity<FareDTO> getFareByAddress(@RequestParam("sourceAddrId") Long sourceAddrId, @RequestParam("destAddrId") Long destAddrId);

    @PostMapping(FeignInsideProperties.FEIGN_PREFIX + "/stock/lock")
    ServerResponseEntity updateStockLock(@RequestBody LockStockDTO lockStockDTO);

}
