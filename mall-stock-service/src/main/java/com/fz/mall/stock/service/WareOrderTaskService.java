package com.fz.mall.stock.service;

import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.stock.pojo.entity.WareOrderTask;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 库存工作单 服务类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
public interface WareOrderTaskService extends IService<WareOrderTask> {

    PageVO<WareOrderTask> page(SimplePageDTO simplePageDTO);

}
