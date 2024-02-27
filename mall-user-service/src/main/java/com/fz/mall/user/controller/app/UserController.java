package com.fz.mall.user.controller.app;

import com.fz.mall.api.user.dto.UserRegisterDTO;
import com.fz.mall.common.resp.ServRespEntity;
import com.fz.mall.user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 会员 前端控制器
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/list")
    public ServRespEntity list() {
        return ServRespEntity.success(memberService.list());
    }

    @PostMapping("/register")
    public ServRespEntity register(UserRegisterDTO userRegisterDTO) {
        memberService.register(userRegisterDTO);
        return ServRespEntity.success();
    }



}
