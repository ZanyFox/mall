package com.fz.mall.cart.controller.app;

import com.fz.mall.cart.model.CartItem;
import com.fz.mall.cart.service.CartService;
import com.fz.mall.common.resp.ResponseEnum;
import com.fz.mall.common.resp.ServerResponseEntity;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private CartService cartService;


    @PostMapping("/addCartItem")
    public ServerResponseEntity add(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num) {

        if (num <= 0)
            return ServerResponseEntity.fail(ResponseEnum.PURCHASE_QUANTITY_ERROR);

        CartItem cartItem = cartService.addCartItem(skuId, num);
        return ServerResponseEntity.success(cartItem);
    }


    @PostMapping("update")
    public ServerResponseEntity changeChecked(@RequestBody List<Long> skuIds) {

        cartService.updateChecked(skuIds);
        return ServerResponseEntity.success();
    }
}


