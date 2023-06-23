package com.fz.mall.goods.pojo.vo;

import com.fz.mall.goods.pojo.entity.Attr;
import com.fz.mall.goods.pojo.entity.AttrGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AttrGroupWithAttrsVO extends AttrGroup {

    private List<Attr> attrs;
}
