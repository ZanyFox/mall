package com.fz.mall.api.user.dto;

import lombok.Data;

@Data
public class UserLoginDTO {

    private String account;

    private String password;
}
