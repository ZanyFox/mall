package com.fz.mall.common.resp;


import lombok.Getter;

@Getter
public enum ResponseEnum {

    SUCCESS(0, "成功"),

    USER_ERROR(10001, "用户端错误"),
    USER_REGISTER_ERROR(11000, "用户注册错误"),
    USER_REGISTER_NOT_ALLOW_PRIVACY_POLICY(11001, "用户未同意隐私政策"),
    USER_REGISTER_USERNAME_EXIST(11002, "用户名已存在"),
    USER_REGISTER_MOBILE_EXIST(11003, "手机号码已被注册"),
    VERIFICATION_CODE_INVALID(11004, "验证码错误"),
    VERIFICATION_CODE_BUSY(11005, "获取验证码频繁，请稍后重试"),
    SKU_NOT_EXIST(11006, "该商品不存在"),
    SKU_STOCK_LACKING(11007, "该商品库存不足"),
    PURCHASE_QUANTITY_ERROR(11008, "购买商品数量不能小于1"),
    PAY_ERROR(11009, "支付异常"),
    SECKILL_TIME_ERROR(11010, "秒杀活动时间不正确"),
    SECKILL_VERIFY_ERROR(11011, "秒杀校验失败"),
    BEYOND_PURCHASE_LIMIT(11012, "超出购买限制"),
    DELIVERY_ADDRESS_INVALID(11013, "用户收货地址不能为空"),


    USER_LOGIN_ERROR(12000, "用户登录异常"),
    USER_LOGIN_ACCOUNT_NOT_EXIST(12001, "用户账户不存在"),
    USER_LOGIN_PASSWORD_WRONG(12002, "用户密码错误"),

    REQUEST_PARAMETER_ERROR(13000, "请求参数错误"),
    PHONE_NUMBER_EMPTY(13001, "手机号码不能为空"),
    PHONE_NUMBER_FORMAT_ERROR(13002, "手机号码格式不正确"),


    ACCESS_PERMISSION_ERROR(14000, "访问权限异常"),


    ADMIN_ERROR(20001, "管理端异常"),
    SECKILL_SKU_NOT_EXIST_ERROR(20002, "秒杀商品不存在"),

    SERVER_INTERNAL_ERROR(90001, "系统内部异常"),
    SERVER_RPC_ERROR(90002, "远程调用异常"),
    SEND_VERIFICATION_CODE_ERROR(90003, "验证码发送失败");


    private final Integer code;
    private final String message;

    ResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseEnum getResponseEnumByCode(Integer code) {
        for (ResponseEnum value : ResponseEnum.values()) {
            if (value.getCode().equals(code))
                return value;
        }
        return SERVER_INTERNAL_ERROR;
    }

}
