package com.fz.mall.stock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.database.util.PageUtil;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.stock.constant.PurchaseDetailStatusEnum;
import com.fz.mall.stock.constant.PurchaseStatusEnum;
import com.fz.mall.stock.pojo.dto.MergePurchaseDTO;
import com.fz.mall.stock.pojo.dto.PurchaseFinishDTO;
import com.fz.mall.stock.pojo.dto.PurchaseItemFinishDTO;
import com.fz.mall.stock.pojo.entity.Purchase;
import com.fz.mall.stock.pojo.entity.PurchaseDetail;
import com.fz.mall.stock.mapper.PurchaseMapper;
import com.fz.mall.stock.service.PurchaseDetailService;
import com.fz.mall.stock.service.PurchaseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fz.mall.stock.service.WareSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 采购信息 服务实现类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@Service
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, Purchase> implements PurchaseService {

    @Autowired
    private PurchaseDetailService purchaseDetailService;

    @Autowired
    private WareSkuService wareSkuService;

    @Override
    public PageVO<Purchase> page(SimplePageDTO simplePageDTO) {

        Page<Purchase> page = page(PageUtil.newPage(simplePageDTO));

        return PageUtil.pageVO(page);
    }

    @Override
    public PageVO<Purchase> listNotAccepted(SimplePageDTO simplePageDTO, Integer status) {

        LambdaQueryWrapper<Purchase> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 采购单状态为0或者1  表示采购单刚新建或者已分配给某个人
        lambdaQueryWrapper.eq(Purchase::getStatus, 0).or().eq(Purchase::getStatus, 1);
        Page<Purchase> page = page(PageUtil.newPage(simplePageDTO), lambdaQueryWrapper);
        return PageUtil.pageVO(page);
    }

    @Transactional
    @Override
    public void merge(MergePurchaseDTO mergePurchaseDTO) {

        Long purchaseId = mergePurchaseDTO.getPurchaseId();
        if (purchaseId == null) {
            Purchase purchase = new Purchase();
            purchase.setStatus(PurchaseStatusEnum.CREATED.getCode());
            save(purchase);
            purchaseId = purchase.getId();
        } else {
            Purchase purchase = getById(purchaseId);
            if (!(Objects.equals(purchase.getStatus(), PurchaseStatusEnum.CREATED.getCode())
                    || Objects.equals(purchase.getStatus(), PurchaseStatusEnum.ASSIGNED.getCode()))) {
                return;
            }
        }

        List<Long> items = mergePurchaseDTO.getItems();
        Long finalPurchaseId = purchaseId;
        List<PurchaseDetail> purchaseDetails = items.stream().map(item -> {
            PurchaseDetail purchaseDetail = new PurchaseDetail();
            purchaseDetail.setId(item);
            purchaseDetail.setPurchaseId(finalPurchaseId);
            purchaseDetail.setStatus(PurchaseDetailStatusEnum.ASSIGNED.getCode());
            return purchaseDetail;
        }).collect(Collectors.toList());

        purchaseDetailService.updateBatchById(purchaseDetails);
    }

    @Transactional
    @Override
    public void acceptPurchaseOrder(List<Long> ids) {

        // 确认采购单状态
        List<Purchase> purchases = ids.stream().map(this::getById)
                .filter(i -> Objects.equals(i.getStatus(), PurchaseStatusEnum.CREATED.getCode())
                        || Objects.equals(i.getStatus(), PurchaseStatusEnum.ASSIGNED.getCode()))
                .peek(i -> i.setStatus(PurchaseStatusEnum.RECEIVED.getCode()))
                .collect(Collectors.toList());
        // 更新采购单状态
        updateBatchById(purchases);

        purchases.forEach((i) -> {
            List<PurchaseDetail> purchaseDetails = purchaseDetailService.lambdaQuery().eq(PurchaseDetail::getPurchaseId, i.getId()).list();
            List<PurchaseDetail> updatedPurchaseDetails = purchaseDetails.stream().peek(purchaseDetail -> purchaseDetail.setStatus(PurchaseDetailStatusEnum.BUYING.getCode())).collect(Collectors.toList());
            purchaseDetailService.updateBatchById(updatedPurchaseDetails);
        });

    }

    @Transactional
    @Override
    public void finishPurchase(PurchaseFinishDTO purchaseFinishDTO) {


        // 改变采购项状态
        List<PurchaseItemFinishDTO> items = purchaseFinishDTO.getItems();
        List<Long> finishedItemIds = new ArrayList<>();
        boolean error = items.stream().anyMatch(item -> Objects.equals(item.getStatus(), PurchaseDetailStatusEnum.FAILURE.getCode()));

        List<PurchaseDetail> purchaseDetails = items.stream().map((item) -> {
            PurchaseDetail purchaseDetail = new PurchaseDetail();
            purchaseDetail.setId(item.getPurchaseItemId());
            if (Objects.equals(item.getStatus(), PurchaseStatusEnum.FINISHED.getCode())) {
                purchaseDetail.setStatus(PurchaseStatusEnum.FINISHED.getCode());
                finishedItemIds.add(item.getPurchaseItemId());
            } else purchaseDetail.setStatus(PurchaseStatusEnum.FINISHED.getCode());
            return purchaseDetail;
        }).collect(Collectors.toList());

        purchaseDetailService.updateBatchById(purchaseDetails);

        // 入库
        List<PurchaseDetail> finishedPurchaseItems = purchaseDetailService.listByIds(finishedItemIds);
        wareSkuService.addStocks(finishedPurchaseItems);

        // 改变采购单状态
        Purchase purchase = new Purchase();
        purchase.setId(purchase.getId());
        purchase.setStatus(error ? PurchaseStatusEnum.ERROR.getCode() : PurchaseStatusEnum.FINISHED.getCode());
        updateById(purchase);

        // 入库


    }
}
