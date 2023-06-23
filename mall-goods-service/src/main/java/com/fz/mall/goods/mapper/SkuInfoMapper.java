package com.fz.mall.goods.mapper;

import com.fz.mall.api.goods.dto.CartSkuInfoDTO;
import com.fz.mall.goods.pojo.entity.SkuInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * sku信息 Mapper 接口
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
public interface SkuInfoMapper extends BaseMapper<SkuInfo> {

    SkuInfo getSkuBySkuId(Long skuId);

    List<Long> getSkuIdsBySpuId(Long spuId);

    List<CartSkuInfoDTO> getCartSkusByIds(@Param("skuIds") List<Long> skuIds);
}
