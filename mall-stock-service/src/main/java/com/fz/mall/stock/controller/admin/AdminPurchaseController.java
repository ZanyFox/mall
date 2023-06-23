package com.fz.mall.stock.controller.admin;

import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.common.resp.ServerResponseEntity;
import com.fz.mall.stock.pojo.dto.MergePurchaseDTO;
import com.fz.mall.stock.pojo.dto.PurchaseFinishDTO;
import com.fz.mall.stock.pojo.entity.Purchase;
import com.fz.mall.stock.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin/stock/purchase")
public class AdminPurchaseController {

    @Autowired
    private PurchaseService purchaseService;


    @GetMapping("/list")
    public ServerResponseEntity list(SimplePageDTO simplePageDTO) {

        PageVO<Purchase> page = purchaseService.page(simplePageDTO);
        return ServerResponseEntity.success(page);
    }

    @GetMapping("/{purchaseId}")
    public ServerResponseEntity getPurchaseById(@PathVariable Long purchaseId) {

        Purchase purchase = purchaseService.getById(purchaseId);
        return ServerResponseEntity.success(purchase);
    }

    @PostMapping
    public ServerResponseEntity save(@RequestBody Purchase purchase) {
        purchaseService.save(purchase);
        return ServerResponseEntity.success();
    }


    @PutMapping
    public ServerResponseEntity update(@RequestBody Purchase purchase) {
        purchaseService.updateById(purchase);
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    public ServerResponseEntity delete(@RequestBody List<Long> ids) {
        purchaseService.removeBatchByIds(ids);
        return ServerResponseEntity.success();
    }


    @GetMapping("/not-accepted/list")
    public ServerResponseEntity unreceivedPage(SimplePageDTO simplePageDTO, @RequestParam("status") Integer status) {

        PageVO<Purchase> purchasePageVO = purchaseService.listNotAccepted(simplePageDTO, status);
        return ServerResponseEntity.success(purchasePageVO);
    }

    @PostMapping("/merge")
    public ServerResponseEntity merge(@RequestBody MergePurchaseDTO mergePurchaseDTO) {

        purchaseService.merge(mergePurchaseDTO);
        return ServerResponseEntity.success();
    }

    /**
     * 领取采购单
     *
     * @return
     */
    @PostMapping("/receive")
    public ServerResponseEntity receivePurchase(@RequestBody List<Long> ids) {
        purchaseService.acceptPurchaseOrder(ids);
        return ServerResponseEntity.success();
    }

    /**
     * 采购完成
     */
    @PostMapping("/finish")
    public ServerResponseEntity finishPurchase(@RequestBody PurchaseFinishDTO purchaseFinishDTO) {

        purchaseService.finishPurchase(purchaseFinishDTO);
        return ServerResponseEntity.success();
    }
}
