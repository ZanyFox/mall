package com.fz.mall.api.order.feign;


import com.fz.mall.common.feign.FeignInsideProperties;
import com.fz.mall.common.resp.ServRespEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "mall-order-service", contextId = "order")
public interface OrderFeignClient {


    @GetMapping(FeignInsideProperties.FEIGN_INSIDE_PREFIX + "/order/getOrderStatusById")
    ServRespEntity<Integer> getOrderStatusById(String orderSn);
}
