package com.fz.mall.stock.service;

import com.fz.mall.api.dto.FareDTO;
import com.fz.mall.api.dto.LockStockDTO;
import com.fz.mall.api.dto.SkuQuantityDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.stock.pojo.dto.LockStockTaskDTO;
import com.fz.mall.stock.pojo.dto.QuerySkuStockDTO;
import com.fz.mall.stock.pojo.entity.PurchaseDetail;
import com.fz.mall.stock.pojo.entity.WareSku;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品库存 服务类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
public interface WareSkuService extends IService<WareSku> {

    PageVO<WareSku> page(QuerySkuStockDTO skuStockDTO);

    /**
     * 新增库存
     *
     * @param finishedPurchaseItems 已完成采购项集合
     */

    void addStocks(List<PurchaseDetail> finishedPurchaseItems);

    Map<Long, Boolean> getSkuHasStockBySkuIds(List<Long> skuIds);

    Map<Long, Integer> getSkuStockBySkuIds(List<Long> skuIds);

    FareDTO getFareByAddress(Long sourceAddrId, Long destAddrId);

    /**
     * 更新锁定库存信息
     */
    void lockStock(LockStockDTO lockStockDTO);

    void unlockStock(LockStockTaskDTO lockStockTaskDTO);

    Map<Long, List<Long>> getWareIdHasStock(List<SkuQuantityDTO> skuQuantities);

    void deductStock(String orderSn);
}
