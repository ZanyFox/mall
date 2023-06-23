package com.fz.mall.auth.controller.app;


import cn.hutool.core.util.PhoneUtil;
import com.fz.mall.api.user.feign.MemberFeignClient;
import com.fz.mall.auth.pojo.param.UserRegisterParam;
import com.fz.mall.auth.service.AuthService;
import com.fz.mall.auth.service.RegisterService;
import com.fz.mall.common.resp.ServerResponseEntity;
import com.fz.mall.common.resp.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthService authService;

    @Autowired
    MemberFeignClient memberFeignClient;

    @Autowired
    private RegisterService registerService;

    @PostMapping("/login")
    public ServerResponseEntity login() {

        return ServerResponseEntity.success();
    }


    @PostMapping("/send-verification-code")
    public ServerResponseEntity sendVerificationCode(String phoneNum) {


        if (ObjectUtils.isEmpty(phoneNum)) return ServerResponseEntity.fail(ResponseEnum.PHONE_NUMBER_EMPTY);

        if (!PhoneUtil.isMobile(phoneNum))
            return ServerResponseEntity.fail(ResponseEnum.PHONE_NUMBER_FORMAT_ERROR);


        ServerResponseEntity<Boolean> mobileUnique = memberFeignClient.isMobileUnique(phoneNum);
        if (!mobileUnique.getSuccess()) return ServerResponseEntity.fail(ResponseEnum.SERVER_INTERNAL_ERROR);

        if (!mobileUnique.getData()) return ServerResponseEntity.fail(ResponseEnum.USER_REGISTER_MOBILE_EXIST);


        authService.sendVerificationCode(phoneNum);
        return ServerResponseEntity.success("验证码发送成功");

    }




    @PostMapping("/register")
    public ServerResponseEntity register(@Validated @RequestBody UserRegisterParam param) {

        registerService.register(param);
        return ServerResponseEntity.success("注册成功！");
    }

}
