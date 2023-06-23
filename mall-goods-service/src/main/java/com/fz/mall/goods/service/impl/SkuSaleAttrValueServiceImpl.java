package com.fz.mall.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fz.mall.goods.mapper.SkuSaleAttrValueMapper;
import com.fz.mall.goods.pojo.SkuIdSaleAttrValues;
import com.fz.mall.goods.pojo.entity.SkuSaleAttrValue;
import com.fz.mall.goods.pojo.vo.SkuItemVO;
import com.fz.mall.goods.service.SkuSaleAttrValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * sku销售属性&值 服务实现类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Service
public class SkuSaleAttrValueServiceImpl extends ServiceImpl<SkuSaleAttrValueMapper, SkuSaleAttrValue> implements SkuSaleAttrValueService {

    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Override
    public List<SkuSaleAttrValue> getSearchSkuSaleAttrValueBySkuId(Long skuId) {
        return skuSaleAttrValueMapper.getSearchSkuSaleAttrValueBySkuId(skuId);
    }

    @Override
    public List<SkuItemVO.SkuSaleAttrVO> getPurchaseAttrsBySpuId(Long spuId) {
        return skuSaleAttrValueMapper.getPurchaseAttrsBySpuId(spuId);
    }

    @Override
    public List<String> getSaleAttrListBySkuId(Long skuId) {
        return skuSaleAttrValueMapper.getSaleAttrListBySkuId(skuId);
    }

    @Override
    public Map<Long, List<String>> getSaleAttrValuesBySkuIds(List<Long> skuIds) {
        List<SkuIdSaleAttrValues> saleAttrValues = skuSaleAttrValueMapper.getSaleAttrValuesBySkuIds(skuIds);
        return saleAttrValues.stream().collect(Collectors.toMap(SkuIdSaleAttrValues::getSkuId, SkuIdSaleAttrValues::getAttrs));

    }


}
