package com.fz.mall.user.controller.app;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.database.util.PageUtil;
import com.fz.mall.common.resp.ServerResponseEntity;
import com.fz.mall.user.entity.MemberLevel;
import com.fz.mall.user.service.MemberLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user/member-level")
public class MemberLevelController {

    @Autowired
    private MemberLevelService memberLevelService;

    @GetMapping("/list")
    public ServerResponseEntity list(SimplePageDTO simplePageDTO) {
        Page<MemberLevel> page = memberLevelService.page(PageUtil.newPage(simplePageDTO));
        return ServerResponseEntity.success(PageUtil.pageVO(page));
    }

}
