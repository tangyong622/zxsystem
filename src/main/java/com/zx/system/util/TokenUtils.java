package com.zx.system.util;

import com.zx.system.cache.Cache;
import com.zx.system.cache.CacheManager;

/**
 * Created by Administrator on 2018/5/14.
 */
public class TokenUtils {

    //获取token信息
    public static void setCache(String key, Object o) {
        //清除原来的
        CacheManager.clearOnly(key);
        //放入缓存
        CacheManager.putCacheInfo(key, o);
    }

    //获取token信息
    public static void setCache(String key, Object o, long timeOut) {
        //清除原来的
        CacheManager.clearOnly(key);
        //放入缓存
        CacheManager.putCacheInfo(key, o, timeOut);
    }

    //获取token信息
    public static JsonResult getCache(String key) {
        Cache cache = CacheManager.getCache(key);
        if (cache == null) {
            return new JsonResult(400, "token无效");
        } else if (cache.getTimeOut() < System.currentTimeMillis()) {
            return new JsonResult(400, "token已失效");
        }
        //清除原来的
        CacheManager.clearOnly(key);
        //重新放入
        CacheManager.putCacheInfo(key, cache.getValue());
        return new JsonResult(cache.getValue());
    }

    //验证短信
    public static JsonResult validate(String phone, String code) {
        Cache cache = CacheManager.getCache(phone);
        if (cache == null) {
            return new JsonResult(400, "验证码无效");
        } else if (cache.getTimeOut() < System.currentTimeMillis()) {
            return new JsonResult(400, "验证码已失效");
        }
        String sendCode = (String)cache.getValue();
        if(!StringUtils.equals(code,sendCode)){
            return new JsonResult(400,"验证码错误");
        }
        //清除原来的
        CacheManager.clearOnly(phone);
        return new JsonResult(0,"验证码正确");
    }

    //清楚缓存
    public static void clearCache(String key) {

        CacheManager.clearOnly(key);
    }
}
