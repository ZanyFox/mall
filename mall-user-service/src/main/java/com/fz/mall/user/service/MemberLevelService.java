package com.fz.mall.user.service;

import com.fz.mall.api.user.dto.MemberLevelDTO;
import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.user.entity.MemberLevel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员等级 服务类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
public interface MemberLevelService extends IService<MemberLevel> {

    PageVO<MemberLevelDTO> page(SimplePageDTO simplePageDTO);

}
