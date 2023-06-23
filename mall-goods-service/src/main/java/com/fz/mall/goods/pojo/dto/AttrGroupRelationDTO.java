package com.fz.mall.goods.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttrGroupRelationDTO implements Serializable {

    private Long attrId;
    private Long attrGroupId;
}
