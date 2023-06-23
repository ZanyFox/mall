package com.fz.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fz.mall.api.goods.constant.AttrEnum;
import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.database.util.PageUtil;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.goods.pojo.dto.AttrDTO;
import com.fz.mall.goods.pojo.entity.*;
import com.fz.mall.goods.mapper.AttrMapper;
import com.fz.mall.goods.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fz.mall.goods.pojo.vo.AttrVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品属性 服务实现类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Service
public class AttrServiceImpl extends ServiceImpl<AttrMapper, Attr> implements AttrService {

    @Autowired
    private AttrAttrGroupRelationService attrAttrGroupRelationService;

    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductAttrValueService productAttrValueService;


    @Transactional
    @Override
    public void save(AttrDTO attrDTO) {
        Attr attr = new Attr();
        BeanUtils.copyProperties(attrDTO, attr);
        save(attr);
        // 只有规格属性才会关联属性分组
        if (Objects.equals(attrDTO.getAttrType(), AttrEnum.ATTR_BASE.getAttrTypeCode())) {
            AttrAttrGroupRelation attrAttrgroupRelation = new AttrAttrGroupRelation();
            attrAttrgroupRelation.setAttrGroupId(attrDTO.getAttrGroupId());
            attrAttrgroupRelation.setAttrId(attr.getAttrId());
            attrAttrGroupRelationService.save(attrAttrgroupRelation);
        }
    }

    @Override
    public PageVO<AttrVO> page(Long categoryId, String attrType, SimplePageDTO simplePageDTO) {

        LambdaQueryWrapper<Attr> queryWrapper = new LambdaQueryWrapper<>();
        // 如果attrType为base 查询基本属性
        queryWrapper.eq(Attr::getAttrType, attrType.equalsIgnoreCase(AttrEnum.ATTR_BASE.getAttrType()) ? AttrEnum.ATTR_BASE.getAttrTypeCode() : AttrEnum.ATTR_SALE.getAttrTypeCode());
        if (categoryId != null) {
            queryWrapper.eq(Attr::getCategoryId, categoryId);
        }

        if (StringUtils.isNotEmpty(simplePageDTO.getKey())) {
            queryWrapper.and((query) -> query.eq(Attr::getAttrId, simplePageDTO.getKey()).or().like(Attr::getAttrName, simplePageDTO.getKey()));
        }
        Page<Attr> attrPageDTO = PageUtil.newPage(simplePageDTO);
        page(attrPageDTO, queryWrapper);
        List<Attr> records = attrPageDTO.getRecords();
        List<AttrVO> attrVOS = records.stream().map((this::getAttrVOByAttr)).collect(Collectors.toList());
        return PageUtil.pageVO(attrPageDTO, attrVOS);
    }

    @Override
    public AttrVO getAttrInfo(Long attrId) {

        Attr attr = getById(attrId);
        AttrVO attrVO = getAttrVOByAttr(attr);
        Long[] categoryPath;
        if (attrVO != null) {
            categoryPath = categoryService.findCategoryPath(attrVO.getCategoryId());
            attrVO.setCategoryPath(categoryPath);
        }
        return attrVO;
    }

    @Transactional
    @Override
    public void update(AttrDTO attrDTO) {
        Attr attr = new Attr();
        BeanUtils.copyProperties(attrDTO, attr);
        updateById(attr);
        // 更新属性分组关系表 如果原本就没有设置属性 需要判断是否是新增
        AttrAttrGroupRelation relation = new AttrAttrGroupRelation();
        relation.setAttrId(attrDTO.getAttrId());
        relation.setAttrGroupId(attrDTO.getAttrGroupId());

        //  查询属性关联的分组数量
        long count =   attrAttrGroupRelationService.lambdaQuery().eq(AttrAttrGroupRelation::getAttrId, attrDTO.getAttrId()).count();

        // 没有关联执行新增 关联更新
        if (count == 0) {
            attrAttrGroupRelationService.save(relation);
        } else {
            LambdaUpdateWrapper<AttrAttrGroupRelation> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(AttrAttrGroupRelation::getAttrId, attr.getAttrId());
            attrAttrGroupRelationService.update(relation, updateWrapper);
        }
    }

    @Override
    public List<ProductAttrValue> listSpuAttr(Long spuId) {
        return productAttrValueService.listBaseAttrById(spuId);
    }

    /**
     * 根据属性查询出属性所属分组信息 以及属性所属分类的信息
     *
     * @param attr
     * @return
     */
    private AttrVO getAttrVOByAttr(Attr attr) {
        AttrVO attrVO = new AttrVO();
        if (attr == null) return null;
        BeanUtils.copyProperties(attr, attrVO);
        AttrAttrGroupRelation relation = attrAttrGroupRelationService.lambdaQuery().eq(AttrAttrGroupRelation::getAttrId, attr.getAttrId()).one();
        if (relation != null && relation.getAttrGroupId() != null) {
            AttrGroup attrGroup = attrGroupService.getById(relation.getAttrGroupId());
            if (attrGroup != null) {
                attrVO.setAttrGroupId(attrGroup.getAttrGroupId());
                attrVO.setAttrGroupName(attrGroup.getAttrGroupName());
            }
        }
        Category category = categoryService.getById(attr.getCategoryId());
        if (category != null) {
            attrVO.setCategoryName(category.getName());
        }
        return attrVO;
    }


}
