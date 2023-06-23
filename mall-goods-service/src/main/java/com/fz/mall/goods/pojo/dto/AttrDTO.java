package com.fz.mall.goods.pojo.dto;

import com.fz.mall.goods.pojo.entity.Attr;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class AttrDTO extends Attr {


    private Long attrGroupId;

}
