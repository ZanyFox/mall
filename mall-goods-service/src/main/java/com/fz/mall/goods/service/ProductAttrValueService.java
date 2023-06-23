package com.fz.mall.goods.service;

import com.fz.mall.goods.pojo.entity.ProductAttrValue;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * spu属性值 服务类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
public interface ProductAttrValueService extends IService<ProductAttrValue> {

    List<ProductAttrValue> listBaseAttrById(Long spuId);

    void updateSpuAttr(Long spuId, List<ProductAttrValue> productAttrValues);

    List<ProductAttrValue> listSearchAttrBySpuId(Long spuId);
}
