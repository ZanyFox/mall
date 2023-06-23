package com.fz.mall.auth.controller.mvc;

import com.fz.mall.common.security.constant.SecurityConstants;
import com.fz.mall.common.data.vo.UserLoginVO;
import com.fz.mall.auth.pojo.param.UserLoginParam;
import com.fz.mall.auth.service.LoginService;
import com.fz.mall.common.constant.MallSessionConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginWebController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public String login(@Validated UserLoginParam userLoginParam, HttpSession session, HttpServletResponse response) {

        UserLoginVO userLoginVO = loginService.login(userLoginParam);
        session.setAttribute(MallSessionConstants.LOGIN_USER_SESSION_KEY, userLoginVO);

        Cookie accessTokenCookie = new Cookie(SecurityConstants.USER_ACCESS_TOKEN, userLoginVO.getAccessToken());
        accessTokenCookie.setMaxAge(30 * 24 * 3600);
        accessTokenCookie.setDomain("mallmall.com");
        Cookie refreshTokenCookie = new Cookie(SecurityConstants.USER_REFRESH_TOKEN, userLoginVO.getRefreshToken());
        refreshTokenCookie.setMaxAge(60 * 24 * 3600);
        refreshTokenCookie.setDomain("mallmall.com");
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        return "redirect:http://mallmall.com";
    }


    @RequestMapping("/login.html")
    public String loginPage(HttpSession session) {

        Object loginUser = session.getAttribute(MallSessionConstants.LOGIN_USER_SESSION_KEY);
        if (loginUser == null)
            return "login";

        return "redirect:http://mallmall.com";
    }

}
