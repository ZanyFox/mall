package com.fz.mall.auth.controller.app;


import com.fz.mall.common.resp.ServerResponseEntity;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RestController
public class TokenController {

    @PostMapping("/refresh")
    public ServerResponseEntity refresh(HttpServletRequest request) {

//        Cookie[] cookies = request.getCookies();
//        String refreshToken = null;
//        for (Cookie cookie : cookies) {
//            if (cookie.getName().equals("refresh_token") && ObjectUtils.isNotEmpty(cookie.getValue()))
//                refreshToken = cookie.getValue();
//        }
//
//        if (ObjectUtils.isEmpty(refreshToken)) {
//
//        }
        return ServerResponseEntity.success();
    }
}

