package com.fz.mall.user.controller.feign;

import com.fz.mall.api.user.dto.*;
import com.fz.mall.api.user.feign.MemberFeignClient;
import com.fz.mall.common.context.ContextHolder;
import com.fz.mall.common.data.vo.UserLoginVO;
import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.common.resp.ServerResponseEntity;
import com.fz.mall.user.entity.MemberReceiveAddress;
import com.fz.mall.user.service.MemberLevelService;
import com.fz.mall.user.service.MemberReceiveAddressService;
import com.fz.mall.user.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@AllArgsConstructor
public class MemberFeignController implements MemberFeignClient {


    private MemberLevelService memberLevelService;
    private MemberService memberService;
    private MemberReceiveAddressService memberReceiveAddressService;


    @Override
    public ServerResponseEntity<PageVO<MemberLevelDTO>> getMemberLevel(SimplePageDTO simplePageDTO) {
        PageVO<MemberLevelDTO> page = memberLevelService.page(simplePageDTO);
        return ServerResponseEntity.success(page);
    }

    @Override
    public ServerResponseEntity register(UserRegisterDTO userRegisterDTO) {

        memberService.register(userRegisterDTO);
        return ServerResponseEntity.success();
    }

    @Override
    public ServerResponseEntity<Boolean> isMobileUnique(String mobile) {
        boolean isUnique = memberService.isMobileUnique(mobile);
        return ServerResponseEntity.success(isUnique);
    }

    @Override
    public ServerResponseEntity<UserLoginVO> login(UserLoginDTO userLoginDTO) {
        UserLoginVO userLoginVO = memberService.login(userLoginDTO);
        return ServerResponseEntity.success(userLoginVO);
    }

    @Override
    public ServerResponseEntity<UserLoginVO> oauthLogin(UserOauthLoginDTO userOauthLoginDTO) {
        UserLoginVO userLoginVO = memberService.oauthLogin(userOauthLoginDTO);
        log.info(userLoginVO.toString());
        return ServerResponseEntity.success(userLoginVO);
    }

    @Override
    public ServerResponseEntity<List<MemberReceiveAddressDTO>> getCurrentUserAddress() {

        Long uid = ContextHolder.getUser().getUid();
        List<MemberReceiveAddress> addresses = memberReceiveAddressService.lambdaQuery().eq(MemberReceiveAddress::getMemberId, uid).list();
        List<MemberReceiveAddressDTO> addressDTOS = addresses.stream().map(memberReceiveAddress -> {
            MemberReceiveAddressDTO addressDTO = new MemberReceiveAddressDTO();
            BeanUtils.copyProperties(memberReceiveAddress, addressDTO);
            return addressDTO;
        }).collect(Collectors.toList());

        log.info("uid: {} addr info: {}", uid, addressDTOS);
        return ServerResponseEntity.success(addressDTOS);
    }

    @Override
    public ServerResponseEntity<MemberReceiveAddressDTO> getAddressById(Long addrId) {
        MemberReceiveAddress addr = memberReceiveAddressService.getById(addrId);
        MemberReceiveAddressDTO memberReceiveAddressDTO = new MemberReceiveAddressDTO();
        BeanUtils.copyProperties(addr, memberReceiveAddressDTO);
        return ServerResponseEntity.success(memberReceiveAddressDTO);
    }

}
