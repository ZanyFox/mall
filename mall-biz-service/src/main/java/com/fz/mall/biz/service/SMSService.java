package com.fz.mall.biz.service;

import com.alibaba.fastjson2.JSON;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import darabonba.core.client.ClientOverrideConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;


@Slf4j
@Service
public class SMSService {

    public Boolean sendVerificationCode(String phoneNum, String code) {

        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId("LTAI5tPaeL11sZSjKGqGHm6L")
                .accessKeySecret("okb8diBrtJghvFZdxqVSFWKuF1f8sR")
                .build());


        AsyncClient client = AsyncClient.builder()
                .region("cn-hangzhou") // Region ID
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride("dysmsapi.aliyuncs.com")
                                .setConnectTimeout(Duration.ofSeconds(30))
                )
                .build();

        Map<String, String> map = new HashMap<>();
        map.put("code", code);

        // Parameter settings for API request
        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .signName("阿里云短信测试")
                .templateCode("SMS_154950909")
                .phoneNumbers(phoneNum)
                .templateParam(JSON.toJSONString(map))
                .build();

        CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);


        CompletableFuture<Boolean> result = response
                .thenApply(resp -> Objects.equals(resp.getBody().getCode(), "OK"))
                .exceptionally(throwable -> {
                    log.error("验证码发送失败: {}", throwable.getMessage());
                    return false;
                });

        Boolean success = result.join();
        client.close();

        return success;
    }
}
