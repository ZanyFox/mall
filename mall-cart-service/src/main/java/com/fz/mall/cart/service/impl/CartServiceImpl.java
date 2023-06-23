package com.fz.mall.cart.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.fz.mall.api.dto.SkuQuantityDTO;
import com.fz.mall.api.feign.StockFeignClient;
import com.fz.mall.api.goods.dto.CartSkuInfoDTO;
import com.fz.mall.api.goods.dto.SkuInfoDTO;
import com.fz.mall.api.goods.feign.GoodsFeignClient;
import com.fz.mall.cart.model.Cart;
import com.fz.mall.common.redis.pojo.CartCache;
import com.fz.mall.cart.model.CartItem;
import com.fz.mall.cart.service.CartService;
import com.fz.mall.common.redis.constant.CartCacheConstants;
import com.fz.mall.common.exception.MallServerException;
import com.fz.mall.common.context.ContextHolder;
import com.fz.mall.common.redis.RedisCache;
import com.fz.mall.common.resp.ServerResponseEntity;
import com.fz.mall.common.resp.ResponseEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {


    private final GoodsFeignClient goodsFeignClient;

    private final StringRedisTemplate redisTemplate;

    private StockFeignClient stockFeignClient;

    private RedisCache redisCache;

    /**
     * 添加商品到购物车  保存此商品的数量和选中状态
     *
     * @param skuId
     * @param quantity
     * @return
     */
    @Override
    public CartItem addCartItem(Long skuId, Integer quantity) {


        CartItem cartItem = buildCartItem(skuId);

        SkuQuantityDTO skuQuantityDTO = new SkuQuantityDTO();
        skuQuantityDTO.setSkuId(skuId);
        skuQuantityDTO.setQuantity(quantity);
        ServerResponseEntity<Map<Long, List<Long>>> wareHasStockBySkuQuantityResp = stockFeignClient.getWareHasStockBySkuQuantity(Collections.singletonList(skuQuantityDTO));
        Map<Long, List<Long>> skuWareIdsMap = wareHasStockBySkuQuantityResp.getData();

        if (ObjectUtils.isEmpty(skuWareIdsMap.get(skuId)))
            throw new MallServerException(ResponseEnum.SKU_STOCK_LACKING);

        cartItem.setCount(quantity);
        String key = CartCacheConstants.CART + ContextHolder.getUser().getUid();
        Optional<String> cartOptional = Optional.ofNullable((String) redisTemplate.opsForHash().get(key, String.valueOf(skuId)));

        cartOptional.ifPresent(item -> {
            CartCache cartCache = JSON.parseObject(item, CartCache.class);
            cartCache.setQuantity(quantity);
            redisCache.putHashObject(key, String.valueOf(skuId), cartCache);
        });


        Map<String, Object> cartItemMap = BeanUtil.beanToMap(cartItem);
        log.info(cartItemMap.toString());

        if (!cartOptional.isPresent()) {
            CartCache cartCache = new CartCache();
            cartCache.setQuantity(quantity);
            cartCache.setChecked(true);
            redisCache.putHashObject(key, String.valueOf(skuId), cartCache);
        }

        return cartItem;
    }

    /**
     * redis中保存商品skuId和对应的数量  这样并没有保存购物车中商品的选中状态
     *
     * @param skuId
     * @return
     */
//    @Override
//    public CartItem addCartItem(Long skuId, Integer num) {
//
//        CartItem cartItem = buildCartItem(skuId);
//        cartItem.setCount(num);
//        String key = CartCacheConstants.CART + UserContextHolder.get().getUid();
//        Optional<Integer> countOptional = Optional.ofNullable((Integer) redisTemplate.opsForHash().get(key, String.valueOf(skuId)));
//
//        countOptional.ifPresent(integer -> {
//            redisTemplate.opsForHash().increment(key, String.valueOf(skuId), num.longValue());
//            cartItem.setCount(integer + num);
//        });
//
//
//        Map<String, Object> cartItemMap = BeanUtil.beanToMap(cartItem);
//        log.info(cartItemMap.toString());
//
//        if (!countOptional.isPresent()) redisTemplate.opsForHash().put(key, String.valueOf(skuId), num);
//
//        return cartItem;
//    }
    @Override
    public CartItem getCartItemBySkuId(Long skuId) {

        String key = CartCacheConstants.CART + ContextHolder.getUser().getUid();
        Optional<CartCache> cartItemOptional = Optional.ofNullable(redisCache.getHashObject(key, String.valueOf(skuId), CartCache.class));
        if (!cartItemOptional.isPresent()) return null;

        CartItem cartItem = buildCartItem(skuId);
        cartItem.setCount(cartItemOptional.get().getQuantity());

        return cartItem;
    }

    @Override
    public Cart getCart() {

        String key = CartCacheConstants.CART + ContextHolder.getUser().getUid();
        List<CartItem> cartItems = getCartItemList(key, false);
        Cart cart = new Cart();
        cart.setItems(cartItems);
        return cart;
    }

    @Override
    public List<CartItem> getUserCheckedCartItems() {

        String key = CartCacheConstants.CART + ContextHolder.getUser().getUid();
        return getCartItemList(key, true);
    }

    @Override
    public void updateChecked(List<Long> skuIds) {

        String key = CartCacheConstants.CART + ContextHolder.getUser().getUid();
        Map<Object, Object> cartCacheMap = redisTemplate.opsForHash().entries(key);

        cartCacheMap.forEach((hashKey, value) -> {
            CartCache cartCache = JSON.parseObject(String.valueOf(value), CartCache.class);
            cartCache.setChecked(!cartCache.getChecked());
            cartCacheMap.replace(hashKey, JSON.toJSONString(cartCache));
        });

        redisTemplate.opsForHash().putAll(key, cartCacheMap);

    }

    /**
     * 获取购物车中的购物项
     *
     * @param key
     * @param checked 是否只获取选中的购物项
     * @return
     */
    private List<CartItem> getCartItemList(String key, boolean checked) {

        Map<Object, Object> cartItemMap = redisTemplate.opsForHash().entries(key);

        if (ObjectUtils.isEmpty(cartItemMap.entrySet())) return null;

        Map<Long, CartCache> skuIdCartMap = cartItemMap.entrySet().stream()
                .collect(Collectors.toMap((entry) -> Long.valueOf((String) entry.getKey()), (entry) -> JSON.parseObject(String.valueOf(entry.getValue()), CartCache.class)));


        List<SkuQuantityDTO> skuQuantityDTOS = skuIdCartMap.entrySet().stream()
                .filter((item) -> !checked || item.getValue().getChecked())
                .map((entry) -> {
                    SkuQuantityDTO skuQuantityDTO = new SkuQuantityDTO();
                    skuQuantityDTO.setSkuId(entry.getKey());
                    skuQuantityDTO.setQuantity(entry.getValue().getQuantity());
                    return skuQuantityDTO;
                }).collect(Collectors.toList());

        List<Long> skuIds = skuQuantityDTOS.stream().map(SkuQuantityDTO::getSkuId).collect(Collectors.toList());

        ServerResponseEntity<Map<Long, List<Long>>> wareHasStockBySkuQuantityResp = stockFeignClient
                .getWareHasStockBySkuQuantity(skuQuantityDTOS);
        Map<Long, List<Long>> skuWareIdsMap = wareHasStockBySkuQuantityResp.getData();


        ServerResponseEntity<Map<Long, List<String>>> skuSaleAttrsMapResp = goodsFeignClient.getSkuSaleAttrsBySkuIds(skuIds);
        Map<Long, List<String>> skuIdSaleAttrValuesMap = skuSaleAttrsMapResp.getData();


        return buildCartItems(skuIds).stream().peek(cartItem -> {
            boolean isChecked = BooleanUtils.isTrue(skuIdCartMap.get(cartItem.getSkuId()).getChecked());
            cartItem.setCount(skuIdCartMap.get(cartItem.getSkuId()).getQuantity());
            cartItem.setCheck(isChecked);
            cartItem.setHasStock(ObjectUtils.isNotEmpty(skuWareIdsMap.get(cartItem.getSkuId())));
            cartItem.setSkuAttrValues(skuIdSaleAttrValuesMap.get(cartItem.getSkuId()));
        }).collect(Collectors.toList());

    }


    private List<CartItem> buildCartItems(List<Long> skuIds) {

        ServerResponseEntity<List<CartSkuInfoDTO>> cartSkuInfosResp = goodsFeignClient.getCartSkuInfosByIds(skuIds);
        List<CartSkuInfoDTO> cartSkus = cartSkuInfosResp.getData();

        return cartSkus.stream().map(cartSkuInfoDTO -> {
            CartItem cartItem = new CartItem();
            cartItem.setTitle(cartSkuInfoDTO.getSkuTitle());
            cartItem.setImage(cartSkuInfoDTO.getSkuDefaultImg());
            cartItem.setPrice(cartSkuInfoDTO.getPrice());
            cartItem.setSkuId(cartSkuInfoDTO.getSkuId());
            return cartItem;
        }).collect(Collectors.toList());

    }


    private CartItem buildCartItem(Long skuId) {

        ServerResponseEntity<SkuInfoDTO> skuInfoResp = goodsFeignClient.getSkuInfoById(skuId);
        if (!skuInfoResp.getSuccess())
            throw new MallServerException(skuInfoResp.getCode());
        SkuInfoDTO skuInfo = skuInfoResp.getData();
        if (skuInfo.getSkuId() == null)
            throw new MallServerException(ResponseEnum.SKU_NOT_EXIST);


        CartItem cartItem = new CartItem();
        cartItem.setCheck(true);
        cartItem.setTitle(skuInfo.getSkuTitle());
        cartItem.setImage(skuInfo.getSkuDefaultImg());
        cartItem.setPrice(skuInfo.getPrice());
        cartItem.setSkuId(skuId);

        ServerResponseEntity<List<String>> skuSaleAttrsResp = goodsFeignClient.getSkuSaleAttrs(skuId);
        List<String> attrs = skuSaleAttrsResp.getData();
        cartItem.setSkuAttrValues(attrs);

        return cartItem;
    }
}
