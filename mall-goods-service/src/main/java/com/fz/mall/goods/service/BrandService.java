package com.fz.mall.goods.service;

import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.goods.pojo.entity.Brand;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 品牌 服务类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
public interface BrandService extends IService<Brand> {

    PageVO<Brand> page(SimplePageDTO simplePageDTO);

    void updateDetail(Brand brand);
}
