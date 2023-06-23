package com.fz.mall.stock.controller.feign;

import com.fz.mall.api.dto.FareDTO;
import com.fz.mall.api.dto.LockStockDTO;
import com.fz.mall.api.dto.SkuQuantityDTO;
import com.fz.mall.api.feign.StockFeignClient;
import com.fz.mall.common.resp.ServerResponseEntity;
import com.fz.mall.stock.service.WareSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class StockFeignController implements StockFeignClient {

    @Autowired
    private WareSkuService wareSkuService;



    @Override
    public ServerResponseEntity<Map<Long, Boolean>> getSkuHasStockBySkuIds(List<Long> skuIds) {

        Map<Long, Boolean> hasStockBySkuIds = wareSkuService.getSkuHasStockBySkuIds(skuIds);
        return ServerResponseEntity.success(hasStockBySkuIds);
    }

    @Override
    public ServerResponseEntity<Map<Long, Integer>> getSkuStockBySkuIds(List<Long> skuIds) {
        Map<Long, Integer> hasStockBySkuIds = wareSkuService.getSkuStockBySkuIds(skuIds);
        return ServerResponseEntity.success(hasStockBySkuIds);
    }

    @Override
    public ServerResponseEntity<Map<Long, List<Long>>> getWareHasStockBySkuQuantity(List<SkuQuantityDTO> skuQuantityDTOS) {
        Map<Long, List<Long>> wareIdHasStock = wareSkuService.getWareIdHasStock(skuQuantityDTOS);
        return ServerResponseEntity.success(wareIdHasStock);
    }

    @Override
    public ServerResponseEntity<FareDTO> getFareByAddress(Long sourceAddrId, Long destAddrId) {

        FareDTO fareDTO = wareSkuService.getFareByAddress(sourceAddrId, destAddrId);
        return ServerResponseEntity.success(fareDTO);
    }

    @Override
    public ServerResponseEntity updateStockLock(LockStockDTO lockStockDTO) {

        wareSkuService.lockStock(lockStockDTO);
        return ServerResponseEntity.success();
    }
}
