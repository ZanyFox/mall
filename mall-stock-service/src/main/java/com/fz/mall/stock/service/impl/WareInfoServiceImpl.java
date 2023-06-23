package com.fz.mall.stock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.database.util.PageUtil;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.stock.pojo.entity.WareInfo;
import com.fz.mall.stock.mapper.WareInfoMapper;
import com.fz.mall.stock.service.WareInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 仓库信息 服务实现类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@Service
public class WareInfoServiceImpl extends ServiceImpl<WareInfoMapper, WareInfo> implements WareInfoService {

    @Override
    public PageVO<WareInfo> list(SimplePageDTO simplePageDTO) {
        LambdaQueryWrapper<WareInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(simplePageDTO.getKey())) {
            lambdaQueryWrapper.and((query) -> query.eq(WareInfo::getId, simplePageDTO.getKey()))
                    .or().like(WareInfo::getName, simplePageDTO.getKey())
                    .or().like(WareInfo::getAreaCode, simplePageDTO.getKey());
        }
        Page<WareInfo> page = page(PageUtil.newPage(simplePageDTO), lambdaQueryWrapper);
        return PageUtil.pageVO(page);
    }
}
