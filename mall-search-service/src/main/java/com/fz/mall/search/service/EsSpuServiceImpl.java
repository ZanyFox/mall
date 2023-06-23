package com.fz.mall.search.service;

import com.alibaba.fastjson2.JSON;
import com.fz.mall.api.goods.feign.GoodsFeignClient;
import com.fz.mall.common.data.bo.EsSkuBO;
import com.fz.mall.common.exception.MallServerException;
import com.fz.mall.common.resp.ServerResponseEntity;
import com.fz.mall.search.constant.EsConstants;
import com.fz.mall.search.service.impl.EsSpuService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Slf4j
@Service
public class EsSpuServiceImpl implements EsSpuService {


    @Autowired
    private RestHighLevelClient restHighLevelClient;


    @Autowired
    private GoodsFeignClient goodsFeignClient;

    @Override
    public void saveEsSpuBO(Long spuId) {

        List<EsSkuBO> esSkuBOS = null;
        try {
            ServerResponseEntity<List<EsSkuBO>> esSkuBOsBySpuId = goodsFeignClient.getEsSkuBOsBySpuId(spuId);
            if (esSkuBOsBySpuId.getSuccess()) {
                esSkuBOS = esSkuBOsBySpuId.getData();
            }
        } catch (Exception e) {
            log.error("远程调用异常: {}", e.toString());
            throw new MallServerException("远程调用异常");
        }

        if (ObjectUtils.isEmpty(esSkuBOS))
            return;

        try {
            BulkRequest bulkRequest = new BulkRequest();
            esSkuBOS.forEach(esSkuBO -> {
                IndexRequest indexRequest = new IndexRequest(EsConstants.INDEX_GOODS);
                indexRequest.id(esSkuBO.getSkuId().toString()).source(JSON.toJSONString(esSkuBO), XContentType.JSON);
                bulkRequest.add(indexRequest);
            });
            BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            if (bulkResponse.hasFailures()) {
                log.error("sku保存到es中发生异常：{}", esSkuBOS);
            }
        } catch (Exception e) {
            log.error("保存到Es异常: {}", e.toString());
            throw new MallServerException("远程调用异常");
        }

    }
}
