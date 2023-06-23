package com.fz.mall.goods.service;

import com.fz.mall.api.goods.constant.AttrEnum;
import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.goods.pojo.dto.AttrGroupRelationDTO;
import com.fz.mall.goods.pojo.entity.Attr;
import com.fz.mall.goods.pojo.entity.AttrGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fz.mall.goods.pojo.vo.AttrGroupWithAttrsVO;
import com.fz.mall.goods.pojo.vo.SkuItemVO;

import java.util.List;

/**
 * <p>
 * 属性分组 服务类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
public interface AttrGroupService extends IService<AttrGroup> {

    /**
     * 根据分类ID获取属性值
     * @param simplePageDTO
     * @return
     */
     PageVO<AttrGroup> page(Long categoryId, SimplePageDTO simplePageDTO);

    List<Attr> getGroupRelatedAttr(Long attrGroupId, AttrEnum attrEnum);


    PageVO<Attr> getNoRelationAttr(Long attrGroupId, SimplePageDTO simplePageDTO);

    void saveBatch(List<AttrGroupRelationDTO> relationDTOS);

    /**
     * 根据分类ID查询分组以及该分组所有的属性
     */
    List<AttrGroupWithAttrsVO> getAttrGroupWithAttrsByCategoryId(Long categoryId, AttrEnum attrEnum);

    void removeRelationBatch(List<AttrGroupRelationDTO> relationDTOS);


    List<SkuItemVO.AttrGroupVO> getSkuAttrGroupWithAllAttrs(Long categoryId, Long skuId, Long spuId);
}
