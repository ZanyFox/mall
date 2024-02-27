package com.fz.mall.stock.controller.app;

import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.common.resp.ServRespEntity;
import com.fz.mall.stock.pojo.dto.QueryPurchaseDetailDTO;
import com.fz.mall.stock.pojo.entity.PurchaseDetail;
import com.fz.mall.stock.service.PurchaseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@RestController
@RequestMapping("/stock/purchase-detail")
public class PurchaseDetailController {


    @Autowired
    private PurchaseDetailService purchaseDetailService;

    @GetMapping("/page")
    private ServRespEntity page(QueryPurchaseDetailDTO queryPurchaseDetailDTO) {
        PageVO<PurchaseDetail> page = purchaseDetailService.page(queryPurchaseDetailDTO);
        return ServRespEntity.success(page);
    }



}
