package com.fz.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fz.mall.api.goods.constant.AttrEnum;
import com.fz.mall.common.database.util.PageUtil;
import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.goods.mapper.AttrAttrGroupRelationMapper;
import com.fz.mall.goods.mapper.AttrGroupMapper;
import com.fz.mall.goods.pojo.dto.AttrGroupRelationDTO;
import com.fz.mall.goods.pojo.entity.Attr;
import com.fz.mall.goods.pojo.entity.AttrAttrGroupRelation;
import com.fz.mall.goods.pojo.entity.AttrGroup;
import com.fz.mall.goods.pojo.vo.AttrGroupWithAttrsVO;
import com.fz.mall.goods.pojo.vo.SkuItemVO;
import com.fz.mall.goods.service.AttrAttrGroupRelationService;
import com.fz.mall.goods.service.AttrGroupService;
import com.fz.mall.goods.service.AttrService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 属性分组 服务实现类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Service
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroup> implements AttrGroupService {

    @Autowired
    private AttrService attrService;

    @Autowired
    private AttrAttrGroupRelationService attrAttrGroupRelationService;

    @Autowired
    private AttrAttrGroupRelationMapper attrAttrGroupRelationMapper;


    @Autowired
    private AttrGroupMapper attrGroupMapper;

    /**
     * 查询 SQL  select * from pms_attr_group where catelog_id = categoryId and (attr_group_id = key or attr_group_name like %key%)
     */
    @Override
    public PageVO<AttrGroup> page(Long categoryId, SimplePageDTO simplePageDTO) {

        LambdaQueryWrapper<AttrGroup> queryWrapper = new LambdaQueryWrapper<>();
        Page<AttrGroup> newPage = PageUtil.newPage(simplePageDTO);

        if (categoryId != 0) {
            queryWrapper.eq(AttrGroup::getCategoryId, categoryId);
        }

        if (ObjectUtils.isNotEmpty(simplePageDTO.getKey())) {
            // 使用 and连接 表示 and (条件)
            queryWrapper.and((query) -> query.eq(AttrGroup::getAttrGroupId, simplePageDTO.getKey())
                    .or().like(AttrGroup::getAttrGroupName, simplePageDTO.getKey()));
        }

        return PageUtil.pageVO(page(newPage, queryWrapper));
    }

    @Override
    public List<Attr> getGroupRelatedAttr(Long attrGroupId, AttrEnum attrEnum) {

        List<AttrAttrGroupRelation> relations = attrAttrGroupRelationService.lambdaQuery()
                .eq(AttrAttrGroupRelation::getAttrGroupId, attrGroupId).list();
        List<Long> attrIds = relations.stream().map(AttrAttrGroupRelation::getAttrId).distinct().collect(Collectors.toList());
        if (ObjectUtils.isEmpty(attrIds)) return null;

        LambdaQueryWrapper<Attr> attrLambdaQueryWrapper = new LambdaQueryWrapper<>();

        switch (attrEnum) {
            case ATTR_BASE:
                attrLambdaQueryWrapper.eq(Attr::getAttrType, AttrEnum.ATTR_BASE.getAttrTypeCode());
                break;
            case ATTR_SALE:
                attrLambdaQueryWrapper.eq(Attr::getAttrType, AttrEnum.ATTR_SALE.getAttrTypeCode());
                break;
            case ATTR_BASE_AND_SALE:
                attrLambdaQueryWrapper.eq(Attr::getAttrType, AttrEnum.ATTR_BASE_AND_SALE.getAttrTypeCode());
                break;
            default:
                break;
        }
        return attrService.list(attrLambdaQueryWrapper.in(Attr::getAttrId, attrIds));
    }


    @Override
    public PageVO<Attr> getNoRelationAttr(Long attrGroupId, SimplePageDTO simplePageDTO) {

        AttrGroup attrGroup = getById(attrGroupId);
        Long categoryId = attrGroup.getCategoryId();

        if (categoryId == null) return null;

        List<Long> attrGroupIds = lambdaQuery().eq(AttrGroup::getCategoryId, categoryId).list()
                .stream().map(AttrGroup::getAttrGroupId)
                .collect(Collectors.toList());


        List<Long> associatedAttrIds = null;

        // 获取已经被关联的属性id  如果这里分组没有强制关联分类  那么可能会查出所有属性
        associatedAttrIds = attrAttrGroupRelationService.lambdaQuery()
                .select(AttrAttrGroupRelation::getAttrId)
                .in(!ObjectUtils.isEmpty(attrGroupIds), AttrAttrGroupRelation::getAttrGroupId, attrGroupIds).list()
                .stream().map(AttrAttrGroupRelation::getAttrId).collect(Collectors.toList());

        LambdaQueryWrapper<Attr> attrQuery = new LambdaQueryWrapper<>();
        attrQuery.eq(Attr::getCategoryId, categoryId)
                // .eq(Attr::getAttrType, AttrEnum.ATTR_BASE.getAttrTypeCode())
                // 强制分组必须关联分类  这里要加attrGroupIds的判空可以不加
                .notIn(!ObjectUtils.isEmpty(attrGroupIds), Attr::getAttrId, associatedAttrIds);

        String key = simplePageDTO.getKey();
        if (ObjectUtils.isNotEmpty(key)) {
            attrQuery.and((queryWrapper -> queryWrapper.eq(Attr::getAttrId, key).or().like(Attr::getAttrName, key)));
        }
        Page<Attr> page = attrService.page(PageUtil.newPage(simplePageDTO), attrQuery);
        return PageUtil.pageVO(page);
    }

    @Override
    public void saveBatch(List<AttrGroupRelationDTO> relationDTOS) {
        attrAttrGroupRelationService.saveBatch(relationDTOS);
    }

    @Override
    public List<AttrGroupWithAttrsVO> getAttrGroupWithAttrsByCategoryId(Long categoryId, AttrEnum attrEnum) {

        return lambdaQuery().eq(AttrGroup::getCategoryId, categoryId).list().stream()
                .map(attrGroup -> {
                    AttrGroupWithAttrsVO attrGroupWithAttrsVO = new AttrGroupWithAttrsVO();
                    List<Attr> attrs = getGroupRelatedAttr(attrGroup.getAttrGroupId(), attrEnum);
                    BeanUtils.copyProperties(attrGroup, attrGroupWithAttrsVO);
                    attrGroupWithAttrsVO.setAttrs(attrs);
                    return attrGroupWithAttrsVO;
                }).filter(attrGroupWithAttrsVO -> !ObjectUtils.isEmpty(attrGroupWithAttrsVO.getAttrs())).collect(Collectors.toList());
    }

    @Override
    public void removeRelationBatch(List<AttrGroupRelationDTO> relationDTOS) {

        attrAttrGroupRelationMapper.deleteBatch(relationDTOS);
    }

    @Override
    public List<SkuItemVO.AttrGroupVO> getSkuAttrGroupWithAllAttrs(Long categoryId, Long skuId, Long spuId) {

        List<SkuItemVO.AttrGroupVO> baseAttrs = attrGroupMapper.getAttrGroupWithBaseAttrs(categoryId, spuId);
        List<SkuItemVO.AttrGroupVO> saleAttrs = attrGroupMapper.getAttrGroupWithSaleAttrs(categoryId, skuId);

        if (ObjectUtils.isEmpty(baseAttrs))
            return saleAttrs;

        saleAttrs.forEach(attrGroupVO1 -> {
            boolean isContain = false;
            for (SkuItemVO.AttrGroupVO baseAttr : baseAttrs) {
                Long attrGroupId = baseAttr.getAttrGroupId();
                if (Objects.equals(attrGroupVO1.getAttrGroupId(), attrGroupId)) {
                    baseAttr.getAttrs().addAll(attrGroupVO1.getAttrs());
                    isContain = true;
                }
            }
            if (!isContain)
                baseAttrs.add(attrGroupVO1);
        });

        return baseAttrs;
    }
}
