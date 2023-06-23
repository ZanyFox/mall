package com.fz.mall.cart.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
public class CartItem {

    private Long skuId;

    private Boolean check = true;

    private String title;

    private String image;

    /**
     * 商品套餐属性
     */
    private List<String> skuAttrValues;

    private BigDecimal price;

    private Integer count;

    private BigDecimal totalPrice;

    private Boolean hasStock;

    public BigDecimal getTotalPrice() {

        return this.price.multiply(new BigDecimal(String.valueOf(this.count)));
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }


}
