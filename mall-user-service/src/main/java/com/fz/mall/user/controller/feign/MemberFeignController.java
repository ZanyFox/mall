package com.fz.mall.user.controller.feign;

import com.fz.mall.api.user.dto.*;
import com.fz.mall.api.user.feign.MemberFeignClient;
import com.fz.mall.common.context.ContextHolder;
import com.fz.mall.common.data.vo.UserLoginVO;
import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.common.resp.ServRespEntity;
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
    public ServRespEntity<PageVO<MemberLevelDTO>> getMemberLevel(SimplePageDTO simplePageDTO) {
        PageVO<MemberLevelDTO> page = memberLevelService.page(simplePageDTO);
        return ServRespEntity.success(page);
    }

    @Override
    public ServRespEntity register(UserRegisterDTO userRegisterDTO) {

        memberService.register(userRegisterDTO);
        return ServRespEntity.success();
    }

    @Override
    public ServRespEntity<Boolean> isMobileUnique(String mobile) {
        boolean isUnique = memberService.isMobileUnique(mobile);
        return ServRespEntity.success(isUnique);
    }

    @Override
    public ServRespEntity<UserLoginVO> login(UserLoginDTO userLoginDTO) {
        UserLoginVO userLoginVO = memberService.login(userLoginDTO);
        return ServRespEntity.success(userLoginVO);
    }

    @Override
    public ServRespEntity<UserLoginVO> oauthLogin(UserOauthLoginDTO userOauthLoginDTO) {
        UserLoginVO userLoginVO = memberService.oauthLogin(userOauthLoginDTO);
        log.info(userLoginVO.toString());
        return ServRespEntity.success(userLoginVO);
    }

    @Override
    public ServRespEntity<List<MemberReceiveAddressDTO>> getCurrentUserAddress() {

        Long uid = ContextHolder.getUser().getUid();
        List<MemberReceiveAddress> addresses = memberReceiveAddressService.lambdaQuery().eq(MemberReceiveAddress::getMemberId, uid).list();
        List<MemberReceiveAddressDTO> addressDTOS = addresses.stream().map(memberReceiveAddress -> {
            MemberReceiveAddressDTO addressDTO = new MemberReceiveAddressDTO();
            BeanUtils.copyProperties(memberReceiveAddress, addressDTO);
            return addressDTO;
        }).collect(Collectors.toList());

        log.info("uid: {} addr info: {}", uid, addressDTOS);
        return ServRespEntity.success(addressDTOS);
    }

    @Override
    public ServRespEntity<MemberReceiveAddressDTO> getAddressById(Long addrId) {
        MemberReceiveAddress addr = memberReceiveAddressService.getById(addrId);
        MemberReceiveAddressDTO memberReceiveAddressDTO = new MemberReceiveAddressDTO();
        BeanUtils.copyProperties(addr, memberReceiveAddressDTO);
        return ServRespEntity.success(memberReceiveAddressDTO);
    }

}
