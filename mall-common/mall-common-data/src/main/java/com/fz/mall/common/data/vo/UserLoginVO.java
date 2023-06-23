package com.fz.mall.common.data.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginVO implements Serializable {

    private Long uid;

    private String username;

    private String nickname;

    private String accessToken;

    private String refreshToken;
}
