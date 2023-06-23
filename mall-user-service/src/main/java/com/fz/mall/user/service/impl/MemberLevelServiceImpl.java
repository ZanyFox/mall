package com.fz.mall.user.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fz.mall.api.user.dto.MemberLevelDTO;
import com.fz.mall.common.database.util.PageUtil;
import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.user.entity.MemberLevel;
import com.fz.mall.user.mapper.MemberLevelMapper;
import com.fz.mall.user.service.MemberLevelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员等级 服务实现类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Service
public class MemberLevelServiceImpl extends ServiceImpl<MemberLevelMapper, MemberLevel> implements MemberLevelService {


    @Override
    public PageVO<MemberLevelDTO> page(SimplePageDTO simplePageDTO) {

        Page<MemberLevel> page = page(PageUtil.newPage(simplePageDTO));

        return PageUtil.pageVO(page, (item) -> {
            MemberLevelDTO memberLevelDTO = new MemberLevelDTO();
            BeanUtils.copyProperties(item, memberLevelDTO);
            return memberLevelDTO;
        });
    }
}
