package com.fz.mall.common.security.filter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.fz.mall.common.constant.MallSessionConstants;
import com.fz.mall.common.context.UserContext;
import com.fz.mall.common.data.vo.UserLoginVO;
import com.fz.mall.common.context.ContextHolder;
import com.fz.mall.common.feign.FeignInsideProperties;
import com.fz.mall.common.security.config.AuthPathAdapter;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class UserLoginFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(UserLoginFilter.class);


    private final List<AuthPathAdapter> authPathAdapters;


    public UserLoginFilter(List<AuthPathAdapter> authPathAdapters) {
        this.authPathAdapters = authPathAdapters;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        logger.info(request.getRequestURI() + "  " + Thread.currentThread().getName() + "   " + Thread.currentThread().getId());


//        List<String> excludePaths = authPathAdapters.stream()
//                .flatMap((Function<AuthPathAdapter, Stream<String>>) authPathAdapter -> authPathAdapter.excludePathPattern().stream())
//                .collect(Collectors.toList());
//
//        if (CollectionUtil.isNotEmpty(excludePaths)) {
//            for (String excludePathPattern : excludePaths) {
//                AntPathMatcher pathMatcher = new AntPathMatcher();
//                if (pathMatcher.match(excludePathPattern, request.getRequestURI())) {
//                    filterChain.doFilter(request, response);
//                    return;
//                }
//            }
//        }

        HttpSession session = request.getSession();
        Map loginUserMap = (Map) session.getAttribute(MallSessionConstants.LOGIN_USER_SESSION_KEY);

        UserContext userContext = new UserContext();

        if (loginUserMap != null) {
            logger.info(loginUserMap.toString());
            UserLoginVO userLoginVO = BeanUtil.toBean(loginUserMap, UserLoginVO.class);
            userContext.setUid(userLoginVO.getUid());
            userContext.setUsername(userLoginVO.getUsername());
        }

        boolean needAddCookie = true;

        if (ObjectUtils.isNotEmpty(request.getCookies())) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("tid") && ObjectUtils.isNotEmpty(cookie.getValue())) {
                    userContext.setTid(cookie.getValue());
                    needAddCookie = false;
                }
            }
        }


        // 分配临时id
        if (ObjectUtils.isEmpty(userContext.getTid())) {
            userContext.setTid(IdUtil.fastSimpleUUID());
        }
        ContextHolder.setUser(userContext);

        if (needAddCookie) {
            // 返回临时id
            Cookie cookie = new Cookie("tid", userContext.getTid());
            cookie.setDomain("mallmall.com");
            cookie.setMaxAge(30 * 24 * 3600);
            response.addCookie(cookie);
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            ContextHolder.clear();
        }

    }
}
