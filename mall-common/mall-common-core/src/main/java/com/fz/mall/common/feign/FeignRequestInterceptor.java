package com.fz.mall.common.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Component
public class FeignRequestInterceptor implements RequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(FeignRequestInterceptor.class);

    @Autowired
    private FeignInsideProperties feignInsideProperties;

    @Override
    public void apply(RequestTemplate template) {

        // feign的内部请求，往请求头放入key 和 secret进行校验
        template.header(feignInsideProperties.getKey(), feignInsideProperties.getSecret());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();

        if (attributes == null) {
            return;
        }
        logger.info("feign调用,请求路径: {}, 当前线程: {}  cookie: {}", attributes.getRequest().getRequestURI(), Thread.currentThread().getName(), attributes.getRequest().getHeader("Cookie"));

        HttpServletRequest request = attributes.getRequest();
        template.header("Authorization", request.getHeader("Authorization"));
        template.header("Cookie", request.getHeader("Cookie"));
        template.header("Host", request.getHeader("Host"));

    }
}
