package com.fz.mall.common.rabbitmq.constant;

public class GoodsListenerConstants {

    public static final String SEARCH_GOODS_EXCHANGE = "search.goods.exchange";
    public static final String SEARCH__GOODS_INSERT_QUEUE = "search.goods.insert.queue";
    public static final String SEARCH__GOODS_UPDATE_QUEUE = "search.goods.update.queue";
    public static final String SEARCH__GOODS_DELETE_QUEUE = "search.goods.delete.queue";

    public static final String SEARCH__GOODS_INSERT_ROUTING_KEY = "search.goods.insert";
    public static final String SEARCH__GOODS_UPDATE_ROUTING_KEY = "search.goods.update";
    public static final String SEARCH__GOODS_DELETE_ROUTING_KEY = "search.goods.delete";
}
