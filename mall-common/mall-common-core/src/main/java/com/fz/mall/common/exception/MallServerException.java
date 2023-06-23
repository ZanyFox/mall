package com.fz.mall.common.exception;


import com.fz.mall.common.resp.ResponseEnum;

public class MallServerException extends RuntimeException {

    public ResponseEnum getServerResponseEnum() {
        return responseEnum;
    }

    private ResponseEnum responseEnum;

    public MallServerException(String msg) {
        super(msg);
    }

    public MallServerException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public MallServerException(ResponseEnum responseEnum, String msg) {
        super(msg);
        this.responseEnum = responseEnum;
    }

    public MallServerException(ResponseEnum responseEnum) {
        super(responseEnum.getMessage());
        this.responseEnum = responseEnum;
    }

    public MallServerException(Integer code) {
        super(ResponseEnum.getResponseEnumByCode(code).getMessage());
        this.responseEnum = ResponseEnum.getResponseEnumByCode(code);
    }
}
