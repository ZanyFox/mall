package com.fz.mall.goods.mapper;

import com.fz.mall.goods.pojo.entity.ProductAttrValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * spu属性值 Mapper 接口
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
public interface ProductAttrValueMapper extends BaseMapper<ProductAttrValue> {


    List<ProductAttrValue> getSearchAttrBySpuId(Long spuId);
}
