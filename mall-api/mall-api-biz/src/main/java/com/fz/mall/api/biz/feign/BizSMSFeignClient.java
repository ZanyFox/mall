package com.fz.mall.api.biz.feign;

import com.fz.mall.common.feign.FeignInsideProperties;
import com.fz.mall.common.resp.ServerResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotEmpty;

@FeignClient(value = "mall-biz-service", contextId = "biz-sms")
public interface BizSMSFeignClient {


    @PostMapping(FeignInsideProperties.FEIGN_PREFIX + "/biz/sms/verification-code")
    ServerResponseEntity<String> sendVerificationCode(@RequestParam("phoneNum") String phoneNum, @RequestParam("code") @NotEmpty(message = "验证码不能为空")  String code);
}
