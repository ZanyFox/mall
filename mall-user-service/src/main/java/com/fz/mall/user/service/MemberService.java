package com.fz.mall.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fz.mall.api.user.dto.UserLoginDTO;
import com.fz.mall.api.user.dto.UserOauthLoginDTO;
import com.fz.mall.api.user.dto.UserRegisterDTO;
import com.fz.mall.common.data.vo.UserLoginVO;
import com.fz.mall.user.entity.Member;
import com.fz.mall.user.entity.MemberReceiveAddress;

import java.util.List;

/**
 * <p>
 * 会员 服务类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
public interface MemberService extends IService<Member> {


    void register(UserRegisterDTO userRegisterDTO);


    UserLoginVO login(UserLoginDTO userLoginDTO);

    boolean isUsernameUnique(String username);

    boolean isMobileUnique(String phoneNum);

    UserLoginVO oauthLogin(UserOauthLoginDTO userOauthLoginDTO);

}
