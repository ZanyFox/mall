package com.fz.mall.goods.service.impl;

import com.fz.mall.goods.pojo.entity.SkuImages;
import com.fz.mall.goods.mapper.SkuImagesMapper;
import com.fz.mall.goods.service.SkuImagesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * sku图片 服务实现类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Service
public class SkuImagesServiceImpl extends ServiceImpl<SkuImagesMapper, SkuImages> implements SkuImagesService {


    @Override
    public List<SkuImages> getSkuImagesBySkuId(Long skuId) {

        return lambdaQuery().eq(skuId != null, SkuImages::getSkuId, skuId)
                .list();
    }
}
