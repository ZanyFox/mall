package com.fz.mall.order.config;

import com.fz.mall.common.security.config.AuthPathAdapter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderAuthPath implements AuthPathAdapter {


    @Override
    public List<String> excludePathPattern() {
        List<String> paths = new ArrayList<>();
        paths.add("/paySuccess.html");
        paths.add("/hello");
        return paths;
    }
}
