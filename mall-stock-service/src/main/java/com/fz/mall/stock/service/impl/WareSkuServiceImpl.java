package com.fz.mall.stock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fz.mall.api.dto.FareDTO;
import com.fz.mall.api.dto.LockStockDTO;
import com.fz.mall.api.dto.SkuQuantityDTO;
import com.fz.mall.api.dto.SkuStockDTO;
import com.fz.mall.api.goods.dto.SkuInfoDTO;
import com.fz.mall.api.goods.feign.GoodsFeignClient;
import com.fz.mall.common.database.util.PageUtil;
import com.fz.mall.common.exception.MallServerException;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.common.resp.ResponseEnum;
import com.fz.mall.common.resp.ServRespEntity;
import com.fz.mall.stock.constant.StockLockStatus;
import com.fz.mall.stock.mapper.WareOrderTaskDetailMapper;
import com.fz.mall.stock.mapper.WareSkuMapper;
import com.fz.mall.stock.pojo.WareHasStock;
import com.fz.mall.stock.pojo.dto.LockStockTaskDTO;
import com.fz.mall.stock.pojo.dto.QuerySkuStockDTO;
import com.fz.mall.stock.pojo.dto.SkuHasStockDTO;
import com.fz.mall.stock.pojo.entity.PurchaseDetail;
import com.fz.mall.stock.pojo.entity.WareOrderTask;
import com.fz.mall.stock.pojo.entity.WareOrderTaskDetail;
import com.fz.mall.stock.pojo.entity.WareSku;
import com.fz.mall.stock.service.WareOrderTaskDetailService;
import com.fz.mall.stock.service.WareOrderTaskService;
import com.fz.mall.stock.service.WareSkuService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WareSkuServiceImpl extends ServiceImpl<WareSkuMapper, WareSku> implements WareSkuService {

    private RabbitTemplate rabbitTemplate;

    private WareSkuMapper wareSkuMapper;

    private WareOrderTaskDetailMapper wareOrderTaskDetailMapper;

    private WareOrderTaskService wareOrderTaskService;

    private WareOrderTaskDetailService wareOrderTaskDetailService;

    private GoodsFeignClient goodsFeignClient;


    @Override
    public PageVO<WareSku> page(QuerySkuStockDTO skuStockDTO) {

        LambdaQueryWrapper<WareSku> queryWrapper = new LambdaQueryWrapper<>();
        if (skuStockDTO.getSkuId() != null) {
            queryWrapper.eq(WareSku::getSkuId, skuStockDTO.getSkuId());
        }

        if (skuStockDTO.getWareId() != null) {
            queryWrapper.eq(WareSku::getWareId, skuStockDTO.getWareId());
        }
        return PageUtil.pageVO(page(PageUtil.newPage(skuStockDTO), queryWrapper));
    }

    @Transactional
    @Override
    public void addStocks(List<PurchaseDetail> finishedPurchaseItems) {
        // 如果没有库存记录 新增
        finishedPurchaseItems.forEach((item) -> {
            WareSku wareSku = new WareSku();
            wareSku.setSkuId(item.getSkuId());
            wareSku.setWareId(item.getWareId());
            wareSku.setStockLocked(0);
            ServRespEntity<SkuInfoDTO> servRespEntity = goodsFeignClient.getSkuInfoById(item.getSkuId());
            if (Objects.equals(servRespEntity.getCode(), ResponseEnum.SUCCESS.getCode())) {
                SkuInfoDTO skuInfoDTO = servRespEntity.getData();
                wareSku.setSkuName(skuInfoDTO.getSkuName());
            }

            LambdaUpdateWrapper<WareSku> updateWrapper = new LambdaUpdateWrapper<WareSku>().eq(WareSku::getWareId, item.getWareId()).eq(WareSku::getSkuId, item.getSkuId());
            saveOrUpdate(wareSku, updateWrapper);
        });
    }

    @Override
    public Map<Long, Boolean> getSkuHasStockBySkuIds(List<Long> skuIds) {

        List<SkuHasStockDTO> skuHasStockDTOS = wareSkuMapper.getSkuHasStockBySkuId(skuIds);
        return skuHasStockDTOS.stream().collect(Collectors.toMap(SkuHasStockDTO::getSkuId, SkuHasStockDTO::getHasStock));
    }

    @Override
    public Map<Long, Integer> getSkuStockBySkuIds(List<Long> skuIds) {
        List<SkuStockDTO> skuStockBySkuId = wareSkuMapper.getSkuStockBySkuId(skuIds);
        return skuStockBySkuId.stream().collect(Collectors.toMap(SkuStockDTO::getSkuId, SkuStockDTO::getStock));
    }

    @Override
    public FareDTO getFareByAddress(Long sourceAddrId, Long destAddrId) {
        FareDTO fareDTO = new FareDTO();
        fareDTO.setSourceAddrId(sourceAddrId);
        fareDTO.setDestAddrId(destAddrId);
        return fareDTO;
    }


    @Transactional
    @Override
    public void lockStock(LockStockDTO lockStockDTO) {


        WareOrderTask wareOrderTask = new WareOrderTask();
        wareOrderTask.setOrderSn(lockStockDTO.getOrderSn());

        List<SkuQuantityDTO> skuQuantities = lockStockDTO.getSkuQuantityDTOS();
        Map<Long, List<Long>> skuWareIdsMap = getWareIdHasStock(skuQuantities);

        // 有商品库存不足  直接抛异常
        if (skuWareIdsMap.size() < skuQuantities.size()) throw new MallServerException(ResponseEnum.SKU_STOCK_LACKING);


        List<WareOrderTaskDetail> wareOrderTaskDetails = new ArrayList<>();

        for (SkuQuantityDTO skuQuantityDTO : skuQuantities) {
            List<Long> wareIds = skuWareIdsMap.get(skuQuantityDTO.getSkuId());
            boolean isSkuLocked = false;
            for (Long wareId : wareIds) {
                isSkuLocked = wareSkuMapper.updateStockLock(wareId, skuQuantityDTO.getSkuId(), skuQuantityDTO.getQuantity());
                if (isSkuLocked) {
                    WareOrderTaskDetail wareOrderTaskDetail = new WareOrderTaskDetail();
                    wareOrderTaskDetail.setWareId(wareId);
                    wareOrderTaskDetail.setSkuId(skuQuantityDTO.getSkuId());
                    wareOrderTaskDetail.setQuantity(skuQuantityDTO.getQuantity());
                    wareOrderTaskDetail.setLockStatus(StockLockStatus.LOCKED.getCode());
                    wareOrderTaskDetails.add(wareOrderTaskDetail);
                    break;
                }
            }
            if (!isSkuLocked) {
                throw new MallServerException(ResponseEnum.SKU_STOCK_LACKING);
            }
        }

        // 插入库存工作单
        wareOrderTaskService.save(wareOrderTask);

        wareOrderTaskDetails = wareOrderTaskDetails.stream()
                .peek((item) -> item.setTaskId(wareOrderTask.getId())).collect(Collectors.toList());

        wareOrderTaskDetailService.saveBatch(wareOrderTaskDetails);

        // 发送消息定时自动解锁库存
        // rabbitTemplate.convertAndSend(StockMqConstants.STOCK_EXCHANGE, StockMqConstants.UNLOCK_STOCK_DELAY_ROUTING_KEY, lockStockDTO.getOrderSn());

    }

    @Transactional
    @Override
    public void unlockStock(LockStockTaskDTO lockStockTaskDTO) {

        List<WareOrderTaskDetail> wareOrderTaskDetails =
                lockStockTaskDTO.getWareOrderTaskDetails().stream()
                        .filter((item) -> Objects.equals(item.getLockStatus(), StockLockStatus.LOCKED.getCode())).collect(Collectors.toList());

        if (ObjectUtils.isEmpty(wareOrderTaskDetails)) return;


        List<Long> taskIds = wareOrderTaskDetails.stream().map(WareOrderTaskDetail::getTaskId).collect(Collectors.toList());
        wareOrderTaskDetailService.updateOrderStockItemStatus(taskIds, StockLockStatus.UNLOCKED.getCode());

        for (WareOrderTaskDetail wareOrderTaskDetail : wareOrderTaskDetails) {
            wareSkuMapper.updateStockUnlock(wareOrderTaskDetail);
        }

    }

    @Override
    public Map<Long, List<Long>> getWareIdHasStock(List<SkuQuantityDTO> skuQuantities) {

        if (ObjectUtils.isEmpty(skuQuantities)) return null;
        return wareSkuMapper.getWareIdHasStock(skuQuantities).stream()
                .collect(Collectors.toMap(WareHasStock::getSkuId, WareHasStock::getWareIds));

    }

    @Transactional
    @Override
    public void deductStock(String orderSn) {
        List<WareOrderTaskDetail> wareOrderTaskDetails = wareOrderTaskDetailMapper.getWareOrderTaskDetailByOrderSn(orderSn);
        if (ObjectUtils.isEmpty(wareOrderTaskDetails)) return;
        wareOrderTaskDetailMapper.updateStatusByOrderSn(orderSn, StockLockStatus.DEDUCTED.getCode());
        for (WareOrderTaskDetail wareOrderTaskDetail : wareOrderTaskDetails) {
            wareSkuMapper.updateDeductAndUnlockStock(wareOrderTaskDetail);
        }
    }
}
