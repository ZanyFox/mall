package com.fz.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.database.util.PageUtil;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.goods.pojo.entity.Brand;
import com.fz.mall.goods.mapper.BrandMapper;
import com.fz.mall.goods.service.BrandService;
import com.fz.mall.goods.service.CategoryBrandRelationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 品牌 服务实现类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageVO<Brand> page(SimplePageDTO simplePageDTO) {
        LambdaQueryWrapper<Brand> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(simplePageDTO.getKey())) {
            queryWrapper.eq(Brand::getBrandId, simplePageDTO.getKey()).or().like(Brand::getName, simplePageDTO.getKey());
        }
        Page<Brand> page = page(PageUtil.newPage(simplePageDTO), queryWrapper);
        return PageUtil.pageVO(page);
    }

    @Transactional
    @Override
    public void updateDetail(Brand brand) {
        updateById(brand);
        // 更新 关系表中的品牌数据
        if (StringUtils.isNotEmpty(brand.getName())) {
            categoryBrandRelationService.updateBrand(brand.getBrandId(), brand.getName());
        }
    }
}
