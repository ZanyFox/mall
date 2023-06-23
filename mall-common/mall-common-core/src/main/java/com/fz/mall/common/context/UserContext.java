package com.fz.mall.common.context;

import lombok.Data;

@Data
public class UserContext {

    private Long uid;

    private String username;

    /**
     * 临时用户ID
     */
    private String tid;
}
