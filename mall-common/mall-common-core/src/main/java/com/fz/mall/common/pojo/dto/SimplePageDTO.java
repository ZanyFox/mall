package com.fz.mall.common.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SimplePageDTO implements Serializable {

    private Long size;

    private Long page;

    private String key;

    private String order;

    private String sort;

}


