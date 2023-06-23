package com.fz.mall.stock.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fz.mall.common.database.util.PageUtil;
import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.stock.pojo.entity.WareOrderTask;
import com.fz.mall.stock.mapper.WareOrderTaskMapper;
import com.fz.mall.stock.service.WareOrderTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 库存工作单 服务实现类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@Service
public class WareOrderTaskServiceImpl extends ServiceImpl<WareOrderTaskMapper, WareOrderTask> implements WareOrderTaskService {


    @Override
    public PageVO<WareOrderTask> page(SimplePageDTO simplePageDTO) {

        Page<WareOrderTask> page = page(PageUtil.newPage(simplePageDTO));
        return PageUtil.pageVO(page);
    }
}
