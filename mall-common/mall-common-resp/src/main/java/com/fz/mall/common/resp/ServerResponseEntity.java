package com.fz.mall.common.resp;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

@Data
public class ServerResponseEntity<T> implements Serializable {

    private Integer code;

    private Boolean success;

    private String msg;

    private T data;


    public static <T> ServerResponseEntity<T> success(String msg, T data) {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setCode(ResponseEnum.SUCCESS.getCode());
        serverResponseEntity.setMsg(msg);
        serverResponseEntity.setSuccess(Boolean.TRUE);
        serverResponseEntity.setData(data);
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> success(String msg) {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setCode(ResponseEnum.SUCCESS.getCode());
        serverResponseEntity.setMsg(msg);
        serverResponseEntity.setSuccess(Boolean.TRUE);
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> success() {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setCode(ResponseEnum.SUCCESS.getCode());
        serverResponseEntity.setMsg(ResponseEnum.SUCCESS.getMessage());
        serverResponseEntity.setSuccess(Boolean.TRUE);
        serverResponseEntity.setData(null);
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> success(T data) {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setCode(ResponseEnum.SUCCESS.getCode());
        serverResponseEntity.setMsg(ResponseEnum.SUCCESS.getMessage());
        serverResponseEntity.setSuccess(Boolean.TRUE);
        serverResponseEntity.setData(data);
        return serverResponseEntity;
    }

    public static ServerResponseEntity<Void> fail(Integer code, String message) {
        ServerResponseEntity<Void> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setCode(code);
        serverResponseEntity.setMsg(message);
        serverResponseEntity.setSuccess(Boolean.FALSE);
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> fail(ResponseEnum responseEnum, T data) {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setCode(responseEnum.getCode());
        serverResponseEntity.setMsg(responseEnum.getMessage());
        serverResponseEntity.setSuccess(Boolean.FALSE);
        serverResponseEntity.setData(data);
        return serverResponseEntity;
    }

    public static <T> ServerResponseEntity<T> fail(ResponseEnum responseEnum) {
        ServerResponseEntity<T> serverResponseEntity = new ServerResponseEntity<>();
        serverResponseEntity.setCode(responseEnum.getCode());
        serverResponseEntity.setMsg(responseEnum.getMessage());
        serverResponseEntity.setSuccess(Boolean.FALSE);
        serverResponseEntity.setData(null);
        return serverResponseEntity;
    }

}
