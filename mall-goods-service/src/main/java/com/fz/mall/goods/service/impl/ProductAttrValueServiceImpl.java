package com.fz.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fz.mall.goods.pojo.entity.ProductAttrValue;
import com.fz.mall.goods.mapper.ProductAttrValueMapper;
import com.fz.mall.goods.service.ProductAttrValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * spu属性值 服务实现类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Service
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueMapper, ProductAttrValue> implements ProductAttrValueService {


    @Autowired
    private ProductAttrValueMapper productAttrValueMapper;

    @Override
    public List<ProductAttrValue> listBaseAttrById(Long spuId) {
        return lambdaQuery().eq(ProductAttrValue::getSpuId, spuId).list();
    }

    @Transactional
    @Override
    public void updateSpuAttr(Long spuId, List<ProductAttrValue> productAttrValues) {
        // 删除之前保存的属性
        LambdaQueryWrapper<ProductAttrValue> queryWrapper = new LambdaQueryWrapper<ProductAttrValue>().eq(ProductAttrValue::getSpuId, spuId);
        remove(queryWrapper);
        List<ProductAttrValue> updatedAttrs = productAttrValues.stream().peek((item) -> item.setSpuId(spuId)).collect(Collectors.toList());
        saveBatch(updatedAttrs);
    }

    @Override
    public List<ProductAttrValue> listSearchAttrBySpuId(Long spuId) {
        return productAttrValueMapper.getSearchAttrBySpuId(spuId);
    }
}
