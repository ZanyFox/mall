package com.fz.mall.common.resp;

import lombok.Data;

import java.io.Serializable;

@Data
public class ServRespEntity<T> implements Serializable {

    private Integer code;

    private Boolean success;

    private String msg;

    private T data;


    public static <T> ServRespEntity<T> success(String msg, T data) {
        ServRespEntity<T> servRespEntity = new ServRespEntity<>();
        servRespEntity.setCode(ResponseEnum.SUCCESS.getCode());
        servRespEntity.setMsg(msg);
        servRespEntity.setSuccess(Boolean.TRUE);
        servRespEntity.setData(data);
        return servRespEntity;
    }

    public static <T> ServRespEntity<T> success(String msg) {
        ServRespEntity<T> servRespEntity = new ServRespEntity<>();
        servRespEntity.setCode(ResponseEnum.SUCCESS.getCode());
        servRespEntity.setMsg(msg);
        servRespEntity.setSuccess(Boolean.TRUE);
        return servRespEntity;
    }

    public static <T> ServRespEntity<T> success() {
        ServRespEntity<T> servRespEntity = new ServRespEntity<>();
        servRespEntity.setCode(ResponseEnum.SUCCESS.getCode());
        servRespEntity.setMsg(ResponseEnum.SUCCESS.getMessage());
        servRespEntity.setSuccess(Boolean.TRUE);
        servRespEntity.setData(null);
        return servRespEntity;
    }

    public static <T> ServRespEntity<T> success(T data) {
        ServRespEntity<T> servRespEntity = new ServRespEntity<>();
        servRespEntity.setCode(ResponseEnum.SUCCESS.getCode());
        servRespEntity.setMsg(ResponseEnum.SUCCESS.getMessage());
        servRespEntity.setSuccess(Boolean.TRUE);
        servRespEntity.setData(data);
        return servRespEntity;
    }

    public static ServRespEntity<Void> fail(Integer code, String message) {
        ServRespEntity<Void> servRespEntity = new ServRespEntity<>();
        servRespEntity.setCode(code);
        servRespEntity.setMsg(message);
        servRespEntity.setSuccess(Boolean.FALSE);
        return servRespEntity;
    }

    public static <T> ServRespEntity<T> fail(ResponseEnum responseEnum, T data) {
        ServRespEntity<T> servRespEntity = new ServRespEntity<>();
        servRespEntity.setCode(responseEnum.getCode());
        servRespEntity.setMsg(responseEnum.getMessage());
        servRespEntity.setSuccess(Boolean.FALSE);
        servRespEntity.setData(data);
        return servRespEntity;
    }

    public static <T> ServRespEntity<T> fail(ResponseEnum responseEnum) {
        ServRespEntity<T> servRespEntity = new ServRespEntity<>();
        servRespEntity.setCode(responseEnum.getCode());
        servRespEntity.setMsg(responseEnum.getMessage());
        servRespEntity.setSuccess(Boolean.FALSE);
        servRespEntity.setData(null);
        return servRespEntity;
    }

}
