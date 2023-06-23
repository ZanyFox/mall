package com.fz.mall.user.controller.admin;

import com.fz.mall.common.resp.ServerResponseEntity;
import com.fz.mall.user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin/user/member")
public class AdminMemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/list")
    public ServerResponseEntity list() {
        return ServerResponseEntity.success(memberService.list());
    }

}
