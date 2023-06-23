package com.fz.mall.goods.pojo.vo;

import com.fz.mall.goods.pojo.entity.SkuImages;
import com.fz.mall.goods.pojo.entity.SkuInfo;
import com.fz.mall.goods.pojo.entity.SpuInfoDesc;
import lombok.Data;
import org.thymeleaf.spring5.processor.SpringUErrorsTagProcessor;

import java.io.Serializable;
import java.util.List;

@Data
public class SkuItemVO implements Serializable {

    private SkuInfo info;

    private List<SkuImages> images;

    /**
     * 销售属性的组合 购买的选择
     */
    private List<SkuSaleAttrVO> saleAttr;

    private SpuInfoDesc desc;


    private SeckillSkuVO seckillSkuVo;

    /**
     * 用于展示商品属性信息 包括基本属性和销售属性
     */
    private List<AttrGroupVO> groupAttrs;

    private Boolean hasStock = true;


    @Data
    public static class SkuSaleAttrVO {

        private Long attrId;

        private String attrName;

        private List<SaleAttrValueWithSkuIdsVO> attrValues;
    }


    @Data
    public static class SaleAttrValueWithSkuIdsVO {

        private String attrValue;

        private String skuIds;
    }

    @Data
    public static class AttrGroupVO {

        private Long attrGroupId;
        private String attrGroupName;
        private List<AttrVO> attrs;

    }

    @Data
    public static class AttrVO {

        private String attrName;
        private String attrValue;
    }

}
