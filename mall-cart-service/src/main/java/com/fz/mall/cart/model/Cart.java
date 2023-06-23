package com.fz.mall.cart.model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class Cart {

    /**
     * 购物车子项信息
     */
    List<CartItem> items;

    /**
     * 商品数量
     */
    private Integer countNum;

    /**
     * 商品类型数量
     */
    private Integer countType;

    /**
     * 商品总价
     */
    private BigDecimal totalAmount;

    /**
     * 减免价格
     */
    private BigDecimal reduce = new BigDecimal(0);
    ;

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public Integer getCountNum() {
        return Optional.ofNullable(items)
                .orElse(Collections.emptyList())
                .stream().map(CartItem::getCount)
                .reduce(Integer::sum)
                .orElse(0);
    }

    public Integer getCountType() {
        return items.size();
    }


    public BigDecimal getTotalAmount() {

        BigDecimal total = Optional.ofNullable(items)
                .orElse(Collections.emptyList())
                .stream().filter(CartItem::getCheck).map(CartItem::getTotalPrice)
                .reduce(new BigDecimal(0), BigDecimal::add);
        // 计算优惠后的价格
        return total.subtract(getReduce());
    }

    public BigDecimal getReduce() {
        return reduce;
    }

    public void setReduce(BigDecimal reduce) {
        this.reduce = reduce;
    }
}
