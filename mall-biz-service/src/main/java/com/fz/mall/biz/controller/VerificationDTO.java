package com.fz.mall.biz.controller;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class VerificationDTO {

    private String phone;

    @NotEmpty(message = "验证码不能为空")
    private String code;
}
