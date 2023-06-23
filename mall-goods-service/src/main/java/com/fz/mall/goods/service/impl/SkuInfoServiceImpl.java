package com.fz.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fz.mall.api.goods.dto.CartSkuInfoDTO;
import com.fz.mall.common.database.util.PageUtil;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.goods.mapper.SkuInfoMapper;
import com.fz.mall.goods.pojo.dto.SkuPageDTO;
import com.fz.mall.goods.pojo.entity.SkuInfo;
import com.fz.mall.goods.service.SkuInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * sku信息 服务实现类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {


    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Override
    public PageVO<SkuInfo> page(SkuPageDTO skuPageDTO) {

        LambdaQueryWrapper<SkuInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper
                .and(StringUtils.isNotBlank(skuPageDTO.getKey()), query ->
                        query.eq(SkuInfo::getSkuId, skuPageDTO.getKey()).or().like(SkuInfo::getSkuName, skuPageDTO.getKey()))
                .eq(skuPageDTO.getBrandId() != null, SkuInfo::getBrandId, skuPageDTO.getBrandId())
                .eq(skuPageDTO.getCategoryId() != null, SkuInfo::getCategoryId, skuPageDTO.getCategoryId())
                .ge(skuPageDTO.getMin() != null, SkuInfo::getPrice, skuPageDTO.getMin())
                .le(skuPageDTO.getMax() != null && skuPageDTO.getMax() > 0, SkuInfo::getPrice, skuPageDTO.getMax());


        Page<SkuInfo> page = page(PageUtil.newPage(skuPageDTO), lambdaQueryWrapper);
        return PageUtil.pageVO(page);
    }

    @Override
    public SkuInfo getSkuBySkuId(Long skuId) {
        return getById(skuId);
    }


    @Override
    public List<SkuInfo> getSkusBySpuId(Long spuId) {

        return lambdaQuery().eq(spuId != null, SkuInfo::getSpuId, spuId).list();
    }

    @Override
    public List<Long> getSkuIdsBySpuId(Long spuId) {
        return skuInfoMapper.getSkuIdsBySpuId(spuId);
    }

    @Override
    public List<CartSkuInfoDTO> getCartSkuById(List<Long> skuIds) {
        return skuInfoMapper.getCartSkusByIds(skuIds);
    }
}
