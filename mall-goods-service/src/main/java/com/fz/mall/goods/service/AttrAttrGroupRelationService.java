package com.fz.mall.goods.service;

import com.fz.mall.goods.pojo.dto.AttrGroupRelationDTO;
import com.fz.mall.goods.pojo.entity.AttrAttrGroupRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 属性&属性分组关联 服务类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
public interface AttrAttrGroupRelationService extends IService<AttrAttrGroupRelation> {


    void saveBatch(List<AttrGroupRelationDTO> relationDTOS);
}
