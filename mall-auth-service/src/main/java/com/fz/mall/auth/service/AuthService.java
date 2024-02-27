package com.fz.mall.auth.service;

import com.fz.mall.api.biz.feign.BizSMSFeignClient;
import com.fz.mall.common.redis.constant.UserCacheConstants;
import com.fz.mall.common.exception.MallServerException;
import com.fz.mall.common.resp.ServRespEntity;
import com.fz.mall.common.resp.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private BizSMSFeignClient bizSMSFeignClient;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void sendVerificationCode(String phoneNum) {
        // 将短信验证码存入redis  格式为验证码:时间  时间为获取验证码的时间  如果获取时间小于60 那么则直接获取失败 防止频繁发验证码
        String codeCache = stringRedisTemplate.opsForValue().get(UserCacheConstants.USER_VERIFICATION_CODE + phoneNum);
        if (codeCache != null) {
            String time = codeCache.split("_")[1];
            if (System.currentTimeMillis() - Long.parseLong(time) < 60_000) {
                throw new MallServerException(ResponseEnum.VERIFICATION_CODE_BUSY);
            }
        }


        String code = codeCache == null ? String.valueOf(RandomUtils.nextInt(100000, 999999)) : codeCache.split("_")[0];

        ServRespEntity<String> rpcResult = bizSMSFeignClient.sendVerificationCode(phoneNum, code);
        if (!rpcResult.getSuccess()) {
            log.error("feign调用发送验证失败: {}", rpcResult.getMsg());
            throw new MallServerException(ResponseEnum.SEND_VERIFICATION_CODE_ERROR);
        }
        String codeWithTime = code + "_" + System.currentTimeMillis();
        stringRedisTemplate.opsForValue().set(UserCacheConstants.USER_VERIFICATION_CODE + phoneNum, codeWithTime, 3, TimeUnit.MINUTES);
    }

}
