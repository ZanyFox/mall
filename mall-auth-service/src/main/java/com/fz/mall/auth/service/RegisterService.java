package com.fz.mall.auth.service;

import com.fz.mall.api.user.dto.UserRegisterDTO;
import com.fz.mall.api.user.feign.MemberFeignClient;
import com.fz.mall.auth.pojo.param.UserRegisterParam;
import com.fz.mall.common.redis.constant.UserCacheConstants;
import com.fz.mall.common.exception.MallServerException;
import com.fz.mall.common.resp.ServerResponseEntity;
import com.fz.mall.common.resp.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RegisterService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private MemberFeignClient memberFeignClient;

    public void register(UserRegisterParam param) {

        String codeCache = redisTemplate.opsForValue().get(UserCacheConstants.USER_VERIFICATION_CODE + param.getPhone());

        if (codeCache == null || !param.getCode().equals(codeCache.split("_")[0]))
            throw new MallServerException(ResponseEnum.VERIFICATION_CODE_INVALID);

        redisTemplate.delete(UserCacheConstants.USER_VERIFICATION_CODE);

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setCode(param.getCode());
        userRegisterDTO.setPassword(param.getPassword());
        userRegisterDTO.setMobile(param.getPhone());
        userRegisterDTO.setUsername(param.getUserName());


        // 可以像下面那种方式处理 也可以在service层中不处理  在controller层 try-catch  处理远程调用等异常
        //          ServerResponseEntity result = memberFeignClient.register(userRegisterDTO);
        //            log.info("结果信息: {}", result);
        //            if (!result.getSuccess()) {
        //                ServerResponseEnum responseEnum = ServerResponseEnum.getResponseEnumByCode(result.getCode());
        //                throw new MallServerException(responseEnum);
        //            }

        try {
            ServerResponseEntity result = memberFeignClient.register(userRegisterDTO);
            log.info("结果信息: {}", result);
            if (!result.getSuccess()) {
                ResponseEnum responseEnum = ResponseEnum.getResponseEnumByCode(result.getCode());
                throw new MallServerException(responseEnum);
            }
        } catch (Exception e) {
            if (e instanceof MallServerException)
                throw new MallServerException(((MallServerException) e).getServerResponseEnum());
            else {
                log.error("远程调用注册异常: {}: {}", e.getClass().getName(), e.getMessage());
                throw new MallServerException(ResponseEnum.SERVER_INTERNAL_ERROR);
            }
        }
    }
}
