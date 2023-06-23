package com.fz.mall.user;

import com.fz.mall.api.user.dto.UserLoginDTO;
import com.fz.mall.api.user.feign.MemberFeignClient;
import com.fz.mall.common.resp.ServerResponseEntity;
import com.fz.mall.user.entity.UserOauth;
import com.fz.mall.user.service.MemberService;
import com.fz.mall.user.service.UserOauthService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {

    @Autowired
    private MemberService memberService;

    @Autowired
    private UserOauthService userOauthService;

    @Autowired
    private MemberFeignClient client;
    @Test
    public void testUser() {
        List<UserOauth> list = userOauthService.list();
        System.out.println(list);
    }

    @Test
    public void testLogin() {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setPassword("123456");
        userLoginDTO.setAccount("15310610871");
//        UserLoginVO login = memberService.login(userLoginDTO);
//        ServerResponseEntity<UserLoginVO> login = client.login(userLoginDTO);
//        System.out.println(login);
        ServerResponseEntity userAddress = client.getCurrentUserAddress();
        System.out.println(userAddress);

    }
}
