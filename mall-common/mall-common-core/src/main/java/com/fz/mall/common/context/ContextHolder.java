package com.fz.mall.common.context;


import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ContextHolder {

    private static final ThreadLocal<UserContext> USER_CONTEXT_HOLDER = new ThreadLocal<>();

    private static final ThreadLocal<Map<String, Object>> MAP_HOLDER = new ThreadLocal<>();


    public static void set(String key, Object value) {
        Map<String, Object> map = getLocalMap();
        map.put(key, value == null ? StringUtils.EMPTY : value);
    }


    public static String get(String key) {
        Map<String, Object> map = getLocalMap();

        return ObjectUtils.identityToString(map.getOrDefault(key, StringUtils.EMPTY));
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key, Class<T> clazz) {
        Map<String, Object> map = getLocalMap();
        return (T) (map.getOrDefault(key, null));
    }

    private static Map<String, Object> getLocalMap() {
        Map<String, Object> map = MAP_HOLDER.get();
        if (map == null) {
            map = new ConcurrentHashMap<>();
            MAP_HOLDER.set(map);
        }
        return map;
    }

    public static UserContext getUser() {
        return USER_CONTEXT_HOLDER.get();
    }


    public static void setUser(UserContext userContext) {
        USER_CONTEXT_HOLDER.set(userContext);
    }

    public static void clear() {

        USER_CONTEXT_HOLDER.remove();
        MAP_HOLDER.remove();
    }

}
