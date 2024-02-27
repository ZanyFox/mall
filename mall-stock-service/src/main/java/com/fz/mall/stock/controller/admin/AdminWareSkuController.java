package com.fz.mall.stock.controller.admin;

import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.common.resp.ServRespEntity;
import com.fz.mall.stock.pojo.dto.QuerySkuStockDTO;
import com.fz.mall.stock.pojo.entity.WareSku;
import com.fz.mall.stock.service.WareSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 商品库存 前端控制器
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@RestController
@RequestMapping("/admin/stock/ware-sku")
public class AdminWareSkuController {

    @Autowired
    private WareSkuService wareSkuService;

    @GetMapping("/list")
    public ServRespEntity page(QuerySkuStockDTO skuStockDTO) {

        PageVO<WareSku> page = wareSkuService.page(skuStockDTO);
        return ServRespEntity.success(page);
    }

    @PutMapping
    public ServRespEntity update(@RequestBody WareSku wareSku) {
        wareSkuService.updateById(wareSku);
        return ServRespEntity.success();
    }

    @DeleteMapping
    public ServRespEntity delete(@RequestBody List<Long> ids) {
        wareSkuService.removeBatchByIds(ids);
        return ServRespEntity.success();
    }

}
