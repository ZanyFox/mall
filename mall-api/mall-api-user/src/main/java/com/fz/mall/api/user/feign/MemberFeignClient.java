package com.fz.mall.api.user.feign;

import com.fz.mall.api.user.dto.*;

import com.fz.mall.common.data.vo.UserLoginVO;
import com.fz.mall.common.feign.FeignInsideProperties;
import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.common.resp.ServerResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "mall-user-service", contextId = "user")
public interface MemberFeignClient {


    @GetMapping(FeignInsideProperties.FEIGN_PREFIX + "/user/member-level/list")
    ServerResponseEntity<PageVO<MemberLevelDTO>> getMemberLevel(SimplePageDTO simplePageDTO);

    @PostMapping(FeignInsideProperties.FEIGN_PREFIX + "/user/register")
    ServerResponseEntity register(@RequestBody UserRegisterDTO userRegisterDTO);

    @GetMapping(FeignInsideProperties.FEIGN_PREFIX + "/user/isMobileValid")
    ServerResponseEntity<Boolean> isMobileUnique(String mobile);


    @PostMapping(FeignInsideProperties.FEIGN_PREFIX + "/user/login")
    ServerResponseEntity<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO);

    @PostMapping(FeignInsideProperties.FEIGN_PREFIX + "/user/oauth-login")
    ServerResponseEntity<UserLoginVO> oauthLogin(@RequestBody UserOauthLoginDTO userOauthLoginDTO);

    @GetMapping(FeignInsideProperties.FEIGN_PREFIX + "/user/currentUserAddr")
    ServerResponseEntity<List<MemberReceiveAddressDTO>> getCurrentUserAddress();

    @GetMapping(FeignInsideProperties.FEIGN_PREFIX + "/user/address/{addrId}")
    ServerResponseEntity<MemberReceiveAddressDTO> getAddressById(@PathVariable("addrId") Long addrId);
}
