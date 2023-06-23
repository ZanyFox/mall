package com.fz.mall.stock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.stock.pojo.dto.QueryPurchaseDetailDTO;
import com.fz.mall.stock.pojo.entity.PurchaseDetail;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
public interface PurchaseDetailService extends IService<PurchaseDetail> {


    PageVO<PurchaseDetail> page(QueryPurchaseDetailDTO queryPurchaseDetailDTO);
}
