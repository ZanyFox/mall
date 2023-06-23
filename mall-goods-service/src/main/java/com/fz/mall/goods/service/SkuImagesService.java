package com.fz.mall.goods.service;

import com.fz.mall.goods.pojo.entity.SkuImages;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * sku图片 服务类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
public interface SkuImagesService extends IService<SkuImages> {


    List<SkuImages> getSkuImagesBySkuId(Long skuId);
}
