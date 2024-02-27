package com.fz.mall.api.cart.feign;

import com.fz.mall.api.cart.dto.CartItemDTO;
import com.fz.mall.common.feign.FeignInsideProperties;
import com.fz.mall.common.resp.ServRespEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "mall-cart-service", contextId = "cart")
public interface CartFeignClient {

    @GetMapping(FeignInsideProperties.FEIGN_PREFIX + "/cart/getUserCheckedCartItems")
    ServRespEntity<List<CartItemDTO>> getUserCheckedCartItems();
}
