package com.fz.mall.auth.pojo.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class UserRegisterParam {

    @Length(min = 3, max = 10, message = "用户名长度不符合要求")
    @NotEmpty(message = "用户名不能为空")
    private String userName;

    @Length(min = 6, message = "密码至少为六位")
    @NotEmpty(message = "密码不能为空")
    private String password;

    @Pattern(regexp = "(?:0|86|\\+86)?1[3-9]\\d{9}", message = "手机号格式不正确")
    private String phone;

    @NotEmpty(message = "验证码不能为空")
    private String code;
}
