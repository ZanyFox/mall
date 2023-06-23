package com.fz.mall.api.user.dto;

import lombok.Data;

@Data
public class UserOauthLoginDTO {

    private String oid;


    private String nickName;

    private String avatarUrl;
}
