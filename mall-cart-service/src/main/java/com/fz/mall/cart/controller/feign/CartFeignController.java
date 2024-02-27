package com.fz.mall.cart.controller.feign;

import com.fz.mall.api.cart.dto.CartItemDTO;
import com.fz.mall.api.cart.feign.CartFeignClient;
import com.fz.mall.cart.model.CartItem;
import com.fz.mall.cart.service.CartService;
import com.fz.mall.common.resp.ServRespEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class CartFeignController implements CartFeignClient {


    private CartService cartService;

    @Override
    public ServRespEntity<List<CartItemDTO>> getUserCheckedCartItems() {

        List<CartItem> userCartItems = cartService.getUserCheckedCartItems();

        List<CartItemDTO> cartItemDTOS = userCartItems.stream().map((item) -> {
            CartItemDTO cartItemDTO = new CartItemDTO();
            BeanUtils.copyProperties(item, cartItemDTO);
            return cartItemDTO;
        }).collect(Collectors.toList());
        return ServRespEntity.success(cartItemDTOS);
    }
}
