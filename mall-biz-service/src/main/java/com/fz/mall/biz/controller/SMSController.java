package com.fz.mall.biz.controller;


import com.fz.mall.api.biz.feign.BizSMSFeignClient;
import com.fz.mall.biz.service.SMSService;
import com.fz.mall.common.resp.ServRespEntity;
import com.fz.mall.common.resp.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
public class SMSController implements BizSMSFeignClient {


    @Autowired
    private SMSService smsService;

    /**
     * 发送验证码
     *
     * @return
     */
    @Override
    public ServRespEntity<String> sendVerificationCode(String phoneNum, String code) {
        Boolean success = smsService.sendVerificationCode(phoneNum, code);
        return success ? ServRespEntity.success("发送验证码成功") : ServRespEntity.fail(ResponseEnum.SEND_VERIFICATION_CODE_ERROR);
    }
}
