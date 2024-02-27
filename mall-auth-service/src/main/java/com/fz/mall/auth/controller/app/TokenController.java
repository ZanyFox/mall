package com.fz.mall.auth.controller.app;


import com.fz.mall.common.resp.ServRespEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TokenController {

    @PostMapping("/refresh")
    public ServRespEntity refresh(HttpServletRequest request) {

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
        return ServRespEntity.success();
    }
}

