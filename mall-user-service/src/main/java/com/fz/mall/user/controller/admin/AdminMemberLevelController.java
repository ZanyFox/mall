package com.fz.mall.user.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.database.util.PageUtil;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.common.resp.ServerResponseEntity;
import com.fz.mall.user.entity.MemberLevel;
import com.fz.mall.user.service.MemberLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("admin/user/member-level")
public class AdminMemberLevelController {

    @Autowired
    private MemberLevelService memberLevelService;

    @GetMapping("/list")
    public ServerResponseEntity page(SimplePageDTO simplePageDTO) {
        Page<MemberLevel> page = memberLevelService.page(PageUtil.newPage(simplePageDTO));
        PageVO<MemberLevel> memberLevelPageVO = PageUtil.pageVO(page);
        return ServerResponseEntity.success(memberLevelPageVO);
    }

    @DeleteMapping
    public ServerResponseEntity delete(List<Long> ids) {
        memberLevelService.removeBatchByIds(ids);
        return ServerResponseEntity.success();
    }

    @GetMapping("/{memberLevelId}")
    public ServerResponseEntity getMemberLevelInfo(@PathVariable Long memberLevelId) {
        MemberLevel memberLevel = memberLevelService.getById(memberLevelId);
        return ServerResponseEntity.success(memberLevel);
    }

    @PostMapping
    public ServerResponseEntity save(@RequestBody MemberLevel memberLevel) {
        memberLevelService.save(memberLevel);
        return ServerResponseEntity.success();
    }

    @PutMapping
    public ServerResponseEntity update(@RequestBody MemberLevel memberLevel) {
        memberLevelService.updateById(memberLevel);
        return ServerResponseEntity.success();
    }

}
