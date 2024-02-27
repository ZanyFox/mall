package com.fz.mall.goods.controller.admin;

import com.fz.mall.api.goods.constant.AttrEnum;
import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.common.resp.ServRespEntity;
import com.fz.mall.goods.pojo.dto.AttrGroupRelationDTO;
import com.fz.mall.goods.pojo.entity.Attr;
import com.fz.mall.goods.pojo.entity.AttrAttrGroupRelation;
import com.fz.mall.goods.pojo.entity.AttrGroup;
import com.fz.mall.goods.service.AttrAttrGroupRelationService;
import com.fz.mall.goods.service.AttrGroupService;
import com.fz.mall.goods.pojo.vo.AttrGroupWithAttrsVO;
import com.fz.mall.goods.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 属性分组 前端控制器
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Slf4j
@RestController
@RequestMapping("/admin/goods/attr-group")
public class AdminAttrGroupController {


    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrAttrGroupRelationService attrAttrGroupRelationService;

    /**
     * 如果categoryId为0则表示查询全部
     */
    @GetMapping("/list/{categoryId}")
    public ServRespEntity page(@PathVariable Long categoryId, SimplePageDTO simplePageDTO) {
        PageVO<AttrGroup> page = attrGroupService.page(categoryId, simplePageDTO);
        return ServRespEntity.success(page);
    }

    @PostMapping
    public ServRespEntity save(@RequestBody AttrGroup attrGroup) {

        attrGroupService.save(attrGroup);
        return ServRespEntity.success();
    }

    @PutMapping
    public ServRespEntity update(@RequestBody AttrGroup attrGroup) {

        attrGroupService.updateById(attrGroup);
        return ServRespEntity.success();
    }

    @DeleteMapping
    public ServRespEntity delete(@RequestBody Long[] ids) {

        attrGroupService.removeBatchByIds(Arrays.asList(ids));
        return ServRespEntity.success();
    }

    @GetMapping("/info/{attrGroupId}")
    public ServRespEntity getAttrGroupInfo(@PathVariable Long attrGroupId) {

        AttrGroup attrGroup = attrGroupService.getById(attrGroupId);
        Long[] categoryPath = categoryService.findCategoryPath(attrGroup.getCategoryId());
        attrGroup.setCategoryPath(categoryPath);
        return ServRespEntity.success(attrGroup);
    }

    /**
     * 根据属性分组id获取 关联属性
     * @param attrGroupId
     * @return
     */
    @GetMapping("/{attrGroupId}/attr/relation")
    public ServRespEntity getAttrRelation(@PathVariable Long attrGroupId) {
        List<Attr> attrs = attrGroupService.getGroupRelatedAttr(attrGroupId, AttrEnum.ALL);
        return ServRespEntity.success(attrs);
    }


    /**
     * 获取当前分组没有关联的属性
     */
    @GetMapping("/{attrGroupId}/attr/no-relation")
    public ServRespEntity getAttrWithoutRelation(@PathVariable Long attrGroupId, SimplePageDTO simplePageDTO) {
        PageVO<Attr> noRelationAttr = attrGroupService.getNoRelationAttr(attrGroupId, simplePageDTO);
        return ServRespEntity.success(noRelationAttr);
    }

    /**
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/{categoryId}/attrs")
    public ServRespEntity getAllAttrGroupWithAttrsByCategoryId(@PathVariable Long categoryId) {
        List<AttrGroupWithAttrsVO> groupWithAttrs = attrGroupService.getAttrGroupWithAttrsByCategoryId(categoryId, AttrEnum.ALL);
        return ServRespEntity.success(groupWithAttrs);
    }

    @GetMapping("/{categoryId}/base-attrs")
    public ServRespEntity getBaseAttrGroupWithAttrsByCategoryId(@PathVariable Long categoryId) {
        List<AttrGroupWithAttrsVO> groupWithAttrs = attrGroupService.getAttrGroupWithAttrsByCategoryId(categoryId, AttrEnum.ATTR_BASE);
        return ServRespEntity.success(groupWithAttrs);
    }

    @DeleteMapping("/attr/relation")
    public ServRespEntity deleteAttrRelation(@RequestBody List<AttrGroupRelationDTO> relationDTOS) {
        attrGroupService.removeRelationBatch(relationDTOS);
        return ServRespEntity.success();
    }

    @PostMapping("/attr/relation")
    public ServRespEntity saveAttrRelation(@RequestBody List<AttrGroupRelationDTO> relationDTOS) {

        List<AttrAttrGroupRelation> relations = relationDTOS.stream().map(dto -> {
            AttrAttrGroupRelation relation = new AttrAttrGroupRelation();
            BeanUtils.copyProperties(dto, relation);
            return relation;
        }).collect(Collectors.toList());
        attrAttrGroupRelationService.saveBatch(relations);

        return ServRespEntity.success();
    }


}
