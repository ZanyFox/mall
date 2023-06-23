package com.fz.mall.search.service.impl;

public interface EsSpuService {

    /**
     * 当新增spu时批量保存对应的sku到es中
     */
    void saveEsSpuBO(Long spuId);
}
