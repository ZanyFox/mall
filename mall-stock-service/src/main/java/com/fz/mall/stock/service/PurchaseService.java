package com.fz.mall.stock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.stock.pojo.dto.MergePurchaseDTO;
import com.fz.mall.stock.pojo.dto.PurchaseFinishDTO;
import com.fz.mall.stock.pojo.entity.Purchase;

import java.util.List;

/**
 * <p>
 * 采购信息 服务类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
public interface PurchaseService extends IService<Purchase> {

    PageVO<Purchase> page(SimplePageDTO simplePageDTO);

    PageVO<Purchase> listNotAccepted(SimplePageDTO simplePageDTO, Integer status);

    /**
     * 合并采购需求到采购单
     * @param mergePurchaseDTO
     */
    void merge(MergePurchaseDTO mergePurchaseDTO);

    /**
     * 领取采购单
     */
    void acceptPurchaseOrder(List<Long> ids);

    /**
     * 完成采购
     * @param purchaseFinishDTO
     */
    void finishPurchase(PurchaseFinishDTO purchaseFinishDTO);
}
