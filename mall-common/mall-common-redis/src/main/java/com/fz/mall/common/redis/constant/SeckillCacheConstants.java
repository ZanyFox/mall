package com.fz.mall.common.redis.constant;

public final class SeckillCacheConstants {

    public static final String SECKILL_PREFIX = "seckill:";

    public static final String SECKILL_SESSION_PREFIX = SECKILL_PREFIX + "session:";

    public static final String SECKILL_SKU_PREFIX = SECKILL_PREFIX + "sku:";

    public static final String SECKILL_SKU_STOCK_PREFIX = SECKILL_SKU_PREFIX + "stock:";

    public static final String SECKILL_PURCHASE_COUNT_KEY = SECKILL_PREFIX + "purchase:";
}
