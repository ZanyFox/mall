package com.fz.mall.auth.service;

import cn.hutool.core.util.IdUtil;
import com.fz.mall.api.user.dto.UserLoginDTO;
import com.fz.mall.api.user.feign.MemberFeignClient;
import com.fz.mall.auth.pojo.param.UserLoginParam;
import com.fz.mall.common.context.UserContext;
import com.fz.mall.common.data.vo.UserLoginVO;
import com.fz.mall.common.exception.MallServerException;
import com.fz.mall.common.redis.RedisCache;
import com.fz.mall.common.redis.constant.TokenCacheConstants;
import com.fz.mall.common.resp.ServRespEntity;
import com.fz.mall.common.resp.ResponseEnum;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class LoginService {

    private RedisCache redisCache;

    private MemberFeignClient memberFeignClient;

    private RedisTemplate<String, Object> redisTemplate;

    public UserLoginVO login(UserLoginParam param) {

        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setAccount(param.getAccount());
        userLoginDTO.setPassword(param.getPassword());

        ServRespEntity<UserLoginVO> response = memberFeignClient.login(userLoginDTO);
        if (!response.getSuccess())
            throw new MallServerException(ResponseEnum.getResponseEnumByCode(response.getCode()));
        UserLoginVO userLoginVO = response.getData();

        UserContext userContext = new UserContext();
        userContext.setUid(userLoginVO.getUid());
        userContext.setUsername(userLoginVO.getUsername());

        String accessToken = IdUtil.fastSimpleUUID();
        String refreshToken = IdUtil.fastSimpleUUID();

        userLoginVO.setAccessToken(accessToken);
        userLoginVO.setRefreshToken(refreshToken);

        redisCache.setObject(TokenCacheConstants.ACCESS_TOKEN_KEY + accessToken, userContext, 30 * 24 * 3600, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(TokenCacheConstants.REFRESH_TO_ACCESS + refreshToken, accessToken, 60 * 24 * 3600, TimeUnit.SECONDS);


        return userLoginVO;

    }
}
