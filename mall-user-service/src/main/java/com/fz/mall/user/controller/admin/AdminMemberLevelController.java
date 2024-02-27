package com.fz.mall.user.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.database.util.PageUtil;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.common.resp.ServRespEntity;
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
    public ServRespEntity page(SimplePageDTO simplePageDTO) {
        Page<MemberLevel> page = memberLevelService.page(PageUtil.newPage(simplePageDTO));
        PageVO<MemberLevel> memberLevelPageVO = PageUtil.pageVO(page);
        return ServRespEntity.success(memberLevelPageVO);
    }

    @DeleteMapping
    public ServRespEntity delete(List<Long> ids) {
        memberLevelService.removeBatchByIds(ids);
        return ServRespEntity.success();
    }

    @GetMapping("/{memberLevelId}")
    public ServRespEntity getMemberLevelInfo(@PathVariable Long memberLevelId) {
        MemberLevel memberLevel = memberLevelService.getById(memberLevelId);
        return ServRespEntity.success(memberLevel);
    }

    @PostMapping
    public ServRespEntity save(@RequestBody MemberLevel memberLevel) {
        memberLevelService.save(memberLevel);
        return ServRespEntity.success();
    }

    @PutMapping
    public ServRespEntity update(@RequestBody MemberLevel memberLevel) {
        memberLevelService.updateById(memberLevel);
        return ServRespEntity.success();
    }

}
