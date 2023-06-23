package com.fz.mall.api.goods.feign;

import com.fz.mall.api.goods.dto.CartSkuInfoDTO;
import com.fz.mall.api.goods.dto.OrderSpuInfoDTO;
import com.fz.mall.api.goods.dto.SkuInfoDTO;
import com.fz.mall.common.data.bo.EsSkuBO;
import com.fz.mall.common.feign.FeignInsideProperties;
import com.fz.mall.common.resp.ServerResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "mall-goods-service", contextId = "goods")
public interface GoodsFeignClient {

    /**
     * @param skuId
     * @return
     * @PathVariable("skuId") 这里需要指定value 否则会报错
     */
    @GetMapping(FeignInsideProperties.FEIGN_PREFIX + "/goods/sku-info/{skuId}")
    ServerResponseEntity<SkuInfoDTO> getSkuInfoById(@PathVariable("skuId") Long skuId);

    /**
     * 批量获取商品信息
     * @param skuIds
     * @return
     */
    @GetMapping(FeignInsideProperties.FEIGN_PREFIX + "/goods/sku-info}")
    ServerResponseEntity<List<SkuInfoDTO>> getSkuInfoByIds(@RequestBody List<Long> skuIds);

    /**
     * 根据spuId 获取ES存储对象
     *
     * @param spuId
     * @return
     */
    @GetMapping(FeignInsideProperties.FEIGN_PREFIX + "/goods/spu-info/getEsSkuBOsBySpuId")
    ServerResponseEntity<List<EsSkuBO>> getEsSkuBOsBySpuId(@RequestParam("spuId") Long spuId);

    /**
     * 根据sku 获取销售属性值
     *
     * @param skuId
     * @return
     */
    @GetMapping(FeignInsideProperties.FEIGN_PREFIX + "/goods/attr/{skuId}")
    ServerResponseEntity<List<String>> getSkuSaleAttrs(@PathVariable("skuId") Long skuId);

    /**
     * 根据sku 批量获取销售属性值
     */
    @GetMapping(FeignInsideProperties.FEIGN_PREFIX + "/goods/getAttrValuesBySkuIds")
    ServerResponseEntity<Map<Long, List<String>>> getSkuSaleAttrsBySkuIds(@RequestBody List<Long> skuIds);

    @GetMapping(FeignInsideProperties.FEIGN_PREFIX + "/goods/cartSkus")
    ServerResponseEntity<List<CartSkuInfoDTO>> getCartSkuInfosByIds(@RequestBody List<Long> skuIds);

    @GetMapping(FeignInsideProperties.FEIGN_PREFIX + "/goods/orderSpuInfo")
    ServerResponseEntity<List<OrderSpuInfoDTO>> getOrderSpuInfoBySkuIds(@RequestBody List<Long> skuIds);
}
