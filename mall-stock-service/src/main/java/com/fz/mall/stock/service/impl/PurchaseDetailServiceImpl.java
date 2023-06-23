package com.fz.mall.stock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fz.mall.common.database.util.PageUtil;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.stock.pojo.dto.QueryPurchaseDetailDTO;
import com.fz.mall.stock.pojo.entity.PurchaseDetail;
import com.fz.mall.stock.mapper.PurchaseDetailMapper;
import com.fz.mall.stock.service.PurchaseDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@Service
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailMapper, PurchaseDetail> implements PurchaseDetailService {


    @Override
    public PageVO<PurchaseDetail> page(QueryPurchaseDetailDTO purchaseDTO) {

        LambdaQueryWrapper<PurchaseDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper.and(StringUtils.isNotBlank(purchaseDTO.getKey()),
                        (query) -> query.eq(PurchaseDetail::getPurchaseId, purchaseDTO.getKey()).or().eq(PurchaseDetail::getSkuId, purchaseDTO.getKey()))
                .eq(purchaseDTO.getWareId() != null, PurchaseDetail::getWareId, purchaseDTO.getWareId())
                .eq(purchaseDTO.getStatus() != null, PurchaseDetail::getStatus, purchaseDTO.getStatus());

        Page<PurchaseDetail> page = page(PageUtil.newPage(purchaseDTO), lambdaQueryWrapper);
        return PageUtil.pageVO(page);
    }
}
