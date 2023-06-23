package com.fz.mall.common.redis.constant;

public final class GoodsCacheConstant {

    public static final String GOODS_PREFIX = "goods:";

    public static final String GOODS_CATEGORY_PREFIX = GOODS_PREFIX + "category:";

    /**
     * 种类菜单  一级分类
     */
    public static final String GOODS_CATEGORY_MENU = GOODS_CATEGORY_PREFIX + "menu:";

    /**
     * 种类标题二级菜单包含种类详情
     */
    public static final String GOODS_CATEGORY_TITLE = GOODS_CATEGORY_PREFIX + "title:";

}
