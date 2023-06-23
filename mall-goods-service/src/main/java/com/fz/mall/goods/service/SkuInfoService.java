package com.fz.mall.goods.service;

import com.fz.mall.api.goods.dto.CartSkuInfoDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.goods.pojo.dto.SkuPageDTO;
import com.fz.mall.goods.pojo.entity.SkuInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * sku信息 服务类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
public interface SkuInfoService extends IService<SkuInfo> {

    PageVO<SkuInfo> page(SkuPageDTO skuPageDTO);


    SkuInfo getSkuBySkuId(Long skuId);

    /**
     * 根据spuId获取sku
     * @param spuId
     * @return
     */
    List<SkuInfo> getSkusBySpuId(Long spuId);

    List<Long> getSkuIdsBySpuId(Long spuId);

    List<CartSkuInfoDTO> getCartSkuById(List<Long> skuIds);
}
