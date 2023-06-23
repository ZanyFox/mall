package com.fz.mall.goods.mapper;

import com.fz.mall.goods.pojo.entity.AttrGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fz.mall.goods.pojo.vo.SkuItemVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 属性分组 Mapper 接口
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
public interface AttrGroupMapper extends BaseMapper<AttrGroup> {


    List<SkuItemVO.AttrGroupVO> getAttrGroupWithBaseAttrs(@Param("categoryId") Long categoryId,@Param("spuId") Long spuId);

    List<SkuItemVO.AttrGroupVO> getAttrGroupWithSaleAttrs(@Param("categoryId") Long categoryId,@Param("skuId") Long skuId);
}
