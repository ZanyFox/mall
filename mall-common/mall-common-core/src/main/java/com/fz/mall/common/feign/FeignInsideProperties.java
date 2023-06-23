package com.fz.mall.common.feign;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "feign.internal")
public class FeignInsideProperties {

    public static final String FEIGN_PREFIX = "/feign";

    public static final String FEIGN_INSIDE_PREFIX = FEIGN_PREFIX + "/inside";

    private String key;

    private String secret;

    private List<String> ips;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public List<String> getIps() {
        return ips;
    }

    public void setIps(List<String> ips) {
        this.ips = ips;
    }
}
