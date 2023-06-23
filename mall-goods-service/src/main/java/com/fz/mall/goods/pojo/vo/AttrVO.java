package com.fz.mall.goods.pojo.vo;

import com.fz.mall.goods.pojo.entity.Attr;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class AttrVO extends Attr {

    private Long attrGroupId;
    private String categoryName;
    private String attrGroupName;
    private Long[] categoryPath;
}
