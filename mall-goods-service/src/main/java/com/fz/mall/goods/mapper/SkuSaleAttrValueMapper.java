package com.fz.mall.goods.mapper;

import com.fz.mall.goods.pojo.SkuIdSaleAttrValues;
import com.fz.mall.goods.pojo.entity.SkuSaleAttrValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fz.mall.goods.pojo.vo.SkuItemVO;
import org.apache.ibatis.annotations.Param;


import java.util.List;

/**
 * <p>
 * sku销售属性&值 Mapper 接口
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
public interface SkuSaleAttrValueMapper extends BaseMapper<SkuSaleAttrValue> {

    /**
     * 根据skuId获取 该商品所有可用于检索的属性
     * @param skuId
     * @return
     */
    List<SkuSaleAttrValue> getSearchSkuSaleAttrValueBySkuId(Long skuId);

    /**
     * 根据spuId获取 可选择的购买组合  返回结果包括每种属性 以及包含该属性所有的skuId
     * @param spuId
     * @return
     */
    List<SkuItemVO.SkuSaleAttrVO> getPurchaseAttrsBySpuId(Long spuId);


    List<String> getSaleAttrListBySkuId(Long skuId);


    List<SkuIdSaleAttrValues> getSaleAttrValuesBySkuIds(@Param("skuIds") List<Long> skuIds);

}
