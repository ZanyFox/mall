package com.fz.mall.auth.pojo.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserLoginParam {

    @NotEmpty(message = "用户名不能为空")
    private String account;

    @NotEmpty(message = "密码不能为空")
    private String password;
}
