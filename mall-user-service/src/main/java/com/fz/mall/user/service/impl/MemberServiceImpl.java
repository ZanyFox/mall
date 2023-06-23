package com.fz.mall.user.service.impl;

import com.fz.mall.api.user.dto.UserOauthLoginDTO;
import com.fz.mall.api.user.dto.UserLoginDTO;
import com.fz.mall.api.user.dto.UserRegisterDTO;
import com.fz.mall.common.data.vo.UserLoginVO;
import com.fz.mall.common.exception.MallServerException;
import com.fz.mall.common.resp.ResponseEnum;
import com.fz.mall.user.entity.Member;
import com.fz.mall.user.entity.UserOauth;
import com.fz.mall.user.mapper.MemberMapper;
import com.fz.mall.user.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fz.mall.user.service.UserOauthService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 会员 服务实现类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {



    private final UserOauthService userOauthService;

    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(UserOauthService userOauthService, PasswordEncoder passwordEncoder) {
        this.userOauthService = userOauthService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(UserRegisterDTO userRegisterDTO) {

        Member member = new Member();
        member.setLevelId(1L);

        if (!isUsernameUnique(userRegisterDTO.getUsername())) {
            throw new MallServerException(ResponseEnum.USER_REGISTER_USERNAME_EXIST);
        }

        if (!isMobileUnique(userRegisterDTO.getMobile())) {
            throw new MallServerException(ResponseEnum.USER_REGISTER_MOBILE_EXIST);
        }
        member.setUsername(userRegisterDTO.getUsername());
        member.setMobile(userRegisterDTO.getMobile());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        member.setPassword(encoder.encode(userRegisterDTO.getPassword()));
        save(member);
    }

    @Override
    public UserLoginVO login(UserLoginDTO userLoginDTO) {
        Member member = lambdaQuery()
                .eq(Member::getMobile, userLoginDTO.getAccount()).or()
                .eq(Member::getEmail, userLoginDTO.getAccount()).one();

        if (member == null)
            throw new MallServerException(ResponseEnum.USER_LOGIN_ACCOUNT_NOT_EXIST);


        boolean matches = passwordEncoder.matches(userLoginDTO.getPassword(), member.getPassword());
        if (!matches)
            throw new MallServerException(ResponseEnum.USER_LOGIN_PASSWORD_WRONG);

        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setUsername(member.getUsername());
        userLoginVO.setNickname(member.getNickname());
        userLoginVO.setUid(member.getId());

        return userLoginVO;
    }

    @Override
    public boolean isUsernameUnique(String username) {

        return !lambdaQuery().eq(ObjectUtils.isNotEmpty(username), Member::getUsername, username).exists();
    }

    @Override
    public boolean isMobileUnique(String phoneNum) {
        return !lambdaQuery().eq(ObjectUtils.isNotEmpty(phoneNum), Member::getMobile, phoneNum).exists();
    }

    @Override
    public UserLoginVO oauthLogin(UserOauthLoginDTO userOauthLoginDTO) {

        Member member;
        UserOauth userOauth = userOauthService.lambdaQuery().eq(UserOauth::getOid, userOauthLoginDTO.getOid()).one();
        if (userOauth != null) {
            member = lambdaQuery().eq(Member::getId, userOauth.getUid()).one();
        } else {
            member = new Member();
            member.setNickname(userOauthLoginDTO.getNickName());
            member.setAvatarUrl(userOauthLoginDTO.getAvatarUrl());
            member.setCreateTime(LocalDateTime.now());
            save(member);

            userOauth = new UserOauth();
            userOauth.setOid(userOauthLoginDTO.getOid());
            userOauth.setUid(member.getId());
            userOauthService.save(userOauth);
        }
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setUsername(member.getUsername());
        userLoginVO.setNickname(member.getNickname());
        return userLoginVO;
    }


}
