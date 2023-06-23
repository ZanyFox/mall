package com.fz.mall.auth.controller.mvc;

import com.fz.mall.auth.pojo.param.UserRegisterParam;
import com.fz.mall.auth.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class RegisterWebController {


    @Autowired
    private RegisterService registerService;

    /**
     * 注册页
     *
     * @param param
     * @return
     */
    @PostMapping("/register")
    public String register(@Validated UserRegisterParam param) {

        registerService.register(param);
        return "redirect:/login.html";
    }
}
