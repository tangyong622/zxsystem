package com.zx.system.cache;

import java.util.HashMap;

/**
 * Created by Administrator on 2018/5/14.
 */
public class CacheManager {

    private static HashMap cacheMap = new HashMap();

    /**
     * 单实例构造方法
     */
    private CacheManager() {
        super();
    }

    /**
     * 得到缓存。同步静态方法
     */
    public synchronized static Cache getCache(String key) {

        return (Cache) cacheMap.get(key);
    }

    /**
     *  载入缓存信息（默认3天）
     */
    public static void putCacheInfo(String key, Object obj) {

        cacheMap.put(key, new Cache(key,obj));
    }

    /**
     *  载入缓存信息
     */
    public static void putCacheInfo(String key, Object obj, long timeOut) {

        cacheMap.put(key, new Cache(key,obj,timeOut));
    }

    /**
     * 清除指定的缓存
     */
    public synchronized static void clearOnly(String key) {

        cacheMap.remove(key);
    }

}
