package com.fz.mall.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.goods.pojo.dto.AttrDTO;
import com.fz.mall.goods.pojo.entity.Attr;
import com.fz.mall.goods.pojo.entity.ProductAttrValue;
import com.fz.mall.goods.pojo.vo.AttrVO;

import java.util.List;

/**
 * <p>
 * 商品属性 服务类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
public interface AttrService extends IService<Attr> {

    /**
     * 新增属性以及属性和分组关系
     * @param attrDTO
     */
    void save(AttrDTO attrDTO);

    PageVO<AttrVO> page(Long catalogId, String attrType, SimplePageDTO simplePageDTO);

    AttrVO getAttrInfo(Long attrId);

    /**
     * 更新属性以及 属性和属性分组关系表、属性和分类关系表
     * @param attrDTO
     */
    void update(AttrDTO attrDTO);

    List<ProductAttrValue> listSpuAttr(Long spuId);
}
