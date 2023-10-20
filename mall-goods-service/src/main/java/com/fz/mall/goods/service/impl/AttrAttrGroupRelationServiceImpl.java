package com.fz.mall.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fz.mall.goods.mapper.AttrAttrGroupRelationMapper;
import com.fz.mall.goods.pojo.dto.AttrGroupRelationDTO;
import com.fz.mall.goods.pojo.entity.AttrAttrGroupRelation;
import com.fz.mall.goods.service.AttrAttrGroupRelationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 属性&属性分组关联 服务实现类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Service
public class AttrAttrGroupRelationServiceImpl extends ServiceImpl<AttrAttrGroupRelationMapper, AttrAttrGroupRelation> implements AttrAttrGroupRelationService {



    @Transactional
    @Override
    public void saveBatch(List<AttrGroupRelationDTO> relationDTOS) {
        List<AttrAttrGroupRelation> relations = relationDTOS.stream().map(dto -> {
            AttrAttrGroupRelation relation = new AttrAttrGroupRelation();
            BeanUtils.copyProperties(dto, relation);
            return relation;
        }).collect(Collectors.toList());
        saveBatch(relations);
    }
}
