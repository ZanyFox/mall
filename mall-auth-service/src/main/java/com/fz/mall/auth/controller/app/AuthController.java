package com.fz.mall.auth.controller.app;


import cn.hutool.core.util.PhoneUtil;
import com.fz.mall.api.user.feign.MemberFeignClient;
import com.fz.mall.auth.pojo.param.UserRegisterParam;
import com.fz.mall.auth.service.AuthService;
import com.fz.mall.auth.service.RegisterService;
import com.fz.mall.common.resp.ServRespEntity;
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
    public ServRespEntity login() {

        return ServRespEntity.success();
    }


    @PostMapping("/send-verification-code")
    public ServRespEntity sendVerificationCode(String phoneNum) {


        if (ObjectUtils.isEmpty(phoneNum)) return ServRespEntity.fail(ResponseEnum.PHONE_NUMBER_EMPTY);

        if (!PhoneUtil.isMobile(phoneNum))
            return ServRespEntity.fail(ResponseEnum.PHONE_NUMBER_FORMAT_ERROR);


        ServRespEntity<Boolean> mobileUnique = memberFeignClient.isMobileUnique(phoneNum);
        if (!mobileUnique.getSuccess()) return ServRespEntity.fail(ResponseEnum.SERVER_INTERNAL_ERROR);

        if (!mobileUnique.getData()) return ServRespEntity.fail(ResponseEnum.USER_REGISTER_MOBILE_EXIST);


        authService.sendVerificationCode(phoneNum);
        return ServRespEntity.success("验证码发送成功");

    }




    @PostMapping("/register")
    public ServRespEntity register(@Validated @RequestBody UserRegisterParam param) {

        registerService.register(param);
        return ServRespEntity.success("注册成功！");
    }

}
