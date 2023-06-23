package com.fz.mall.cart.service;

import com.fz.mall.cart.model.Cart;
import com.fz.mall.cart.model.CartItem;

import java.util.List;

public interface CartService {

    /**
     * 添加商品到购物车
     * @param skuId
     * @param num
     * @return
     */
    CartItem addCartItem(Long skuId, Integer num);

    /**
     * 根据skuId获取该商品的购物项
     * @param skuId
     * @return
     */
    CartItem getCartItemBySkuId(Long skuId);

    /**
     * 获取用户购物车信息
     * @return
     */
    Cart getCart();

    /**
     * 获取当前用户的购物项
     * @return
     */
    List<CartItem> getUserCheckedCartItems();

    void updateChecked(List<Long> skuIds);
}
