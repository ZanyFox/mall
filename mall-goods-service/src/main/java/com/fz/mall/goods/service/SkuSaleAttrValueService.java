package com.fz.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fz.mall.goods.pojo.entity.SkuSaleAttrValue;
import com.fz.mall.goods.pojo.vo.SkuItemVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * sku销售属性&值 服务类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
public interface SkuSaleAttrValueService extends IService<SkuSaleAttrValue> {


    List<SkuSaleAttrValue> getSearchSkuSaleAttrValueBySkuId(Long skuId);

    List<SkuItemVO.SkuSaleAttrVO> getPurchaseAttrsBySpuId(Long spuId);

    List<String> getSaleAttrListBySkuId(Long skuId);

    Map<Long, List<String>> getSaleAttrValuesBySkuIds(List<Long> skuIds);
}
