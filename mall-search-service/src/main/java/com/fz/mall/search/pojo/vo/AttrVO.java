package com.fz.mall.search.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class AttrVO {

    private Long attrId;

    private String attrName;

    private List<String> attrValues;
}
