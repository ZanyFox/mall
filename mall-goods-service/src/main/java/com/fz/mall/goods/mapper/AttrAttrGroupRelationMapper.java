package com.fz.mall.goods.mapper;

import com.fz.mall.goods.pojo.dto.AttrGroupRelationDTO;
import com.fz.mall.goods.pojo.entity.AttrAttrGroupRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 属性&属性分组关联 Mapper 接口
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
public interface AttrAttrGroupRelationMapper extends BaseMapper<AttrAttrGroupRelation> {

    void deleteBatch(@Param("relations") List<AttrGroupRelationDTO> relationDTOS);
}
