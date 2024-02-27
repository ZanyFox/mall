package com.fz.mall.stock.controller.admin;

import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.common.resp.ServRespEntity;
import com.fz.mall.stock.pojo.dto.QueryPurchaseDetailDTO;
import com.fz.mall.stock.pojo.entity.PurchaseDetail;
import com.fz.mall.stock.service.PurchaseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@RestController
@RequestMapping("/admin/stock/purchase-detail")
public class AdminPurchaseDetailController {


    @Autowired
    private PurchaseDetailService purchaseDetailService;



    @GetMapping("/list")
    public ServRespEntity list(QueryPurchaseDetailDTO queryPurchaseDetailDTO) {
        PageVO<PurchaseDetail> page = purchaseDetailService.page(queryPurchaseDetailDTO);
        return ServRespEntity.success(page);
    }

    @GetMapping("/{purchaseId}")
    public ServRespEntity getPurchaseDetailById(@PathVariable Long purchaseId) {
        PurchaseDetail purchaseDetail = purchaseDetailService.getById(purchaseId);
        return ServRespEntity.success(purchaseDetail);
    }

    @PostMapping
    public ServRespEntity save(@RequestBody PurchaseDetail detail) {
        purchaseDetailService.save(detail);
        return ServRespEntity.success();
    }

    @DeleteMapping
    public ServRespEntity delete(@RequestBody List<Long> ids) {
        purchaseDetailService.removeBatchByIds(ids);
        return ServRespEntity.success();
    }

    @PutMapping
    public ServRespEntity update(@RequestBody PurchaseDetail detail) {
        purchaseDetailService.updateById(detail);
        return ServRespEntity.success();
    }


}
