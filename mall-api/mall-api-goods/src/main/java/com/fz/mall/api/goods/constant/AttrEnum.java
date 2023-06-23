package com.fz.mall.api.goods.constant;

import lombok.Getter;

@Getter
public enum AttrEnum {

    /**
     * 销售属性
     */
    ATTR_SALE(0, "sale"),
    /**
     * 基本属性
     */
    ATTR_BASE(1, "base"),
    /**
     * 既是基本属性又是销售属性
     */
    ATTR_BASE_AND_SALE(2, "base_and_sale"),
    /**
     * 全部属性
     */
    ALL(3, "all");

    AttrEnum(Integer attrTypeCode, String attrType) {
        this.attrType = attrType;
        this.attrTypeCode = attrTypeCode;
    }

    private final String attrType;
    private final Integer attrTypeCode;
}
