package com.fz.mall.stock.service;

import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.stock.pojo.entity.WareInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 仓库信息 服务类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
public interface WareInfoService extends IService<WareInfo> {

    PageVO<WareInfo> list(SimplePageDTO simplePageDTO);

}
