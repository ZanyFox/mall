package com.fz.mall.auth.controller.mvc;


import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.fz.mall.api.user.dto.UserOauthLoginDTO;
import com.fz.mall.api.user.feign.MemberFeignClient;
import com.fz.mall.common.data.vo.UserLoginVO;
import com.fz.mall.common.constant.MallSessionConstants;
import com.fz.mall.common.resp.ServerResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Controller
public class OauthWebController {


    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private MemberFeignClient memberFeignClient;

    @RequestMapping("/oauth/gitee")
    public String giteeOauth(@RequestParam("code") String code, HttpSession session) {


        Map<String, Object> oauthMap = new HashMap<>();
        oauthMap.put("client_id", "65d4bba3797b4260f9878dc01e1284c8fcbe94bbf18861ebc0036593c5fb9cf8");
        oauthMap.put("client_secret", "038acfa505cd4454da38aa9cf18230a90ce7d213ff72707087fd45e8f1a6fa91");
        oauthMap.put("code", code);
        oauthMap.put("grant_type", "authorization_code");
        oauthMap.put("redirect_uri", "http://auth.mallmall.com/oauth/gitee");


        CompletableFuture<UserLoginVO> future = CompletableFuture.supplyAsync(() -> {
            try (HttpResponse tokenResp = HttpUtil.createPost("https://gitee.com/oauth/token").form(oauthMap).execute()) {
                if (tokenResp.isOk()) {
                    log.info("tokenResp: {}", tokenResp.body());
                    Map result = JSON.parseObject(tokenResp.body(), Map.class);
                    if (result.get("access_token") != null) {
                        return (String) result.get("access_token");
                    }
                }
            }
            return null;
        }, threadPoolExecutor).thenApply(token -> {
            try (HttpResponse userResp = HttpUtil.createGet("https://gitee.com/api/v5/user").form("access_token", token).execute()) {
                if (userResp.isOk()) {
                    log.info("userResp: {}", userResp.body());
                    Map giteeUserMap = JSON.parseObject(userResp.body(), Map.class);
                    String oid = String.valueOf(giteeUserMap.get("id"));
                    String nickName = (String) giteeUserMap.get("login");
                    String avatarUrl = (String) giteeUserMap.get("avatar_url");
                    UserOauthLoginDTO userOauthLoginDTO = new UserOauthLoginDTO();
                    userOauthLoginDTO.setOid(oid);
                    userOauthLoginDTO.setAvatarUrl(avatarUrl);
                    userOauthLoginDTO.setNickName(nickName);
                    return userOauthLoginDTO;
                }
            }
            return null;
        }).thenApply(userOauthLoginDTO -> {
            ServerResponseEntity<UserLoginVO> userLoginVOServerResponseEntity = memberFeignClient.oauthLogin(userOauthLoginDTO);
            if (userLoginVOServerResponseEntity.getSuccess()) {
                return userLoginVOServerResponseEntity.getData();
            }
            return null;
        });

        UserLoginVO userLoginVO = future.join();
        log.info(userLoginVO.toString());
        session.setAttribute(MallSessionConstants.LOGIN_USER_SESSION_KEY, userLoginVO);

        return "redirect:http://mallmall.com";

    }

}
