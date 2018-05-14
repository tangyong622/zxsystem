package com.zx.system.util;

import com.zx.system.cache.Cache;
import com.zx.system.cache.CacheManager;

/**
 * Created by Administrator on 2018/5/14.
 */
public class TokenUtils {

    public static JsonResult getCache(String key){

        Cache cache = CacheManager.getCache(key);
        if(cache == null){
            return new JsonResult(400,"token无效");
        }else if(cache.getTimeOut() >= System.currentTimeMillis()){
            return new JsonResult(400,"token已失效");
        }
        //清除原来的
        CacheManager.clearOnly(key);
        CacheManager.putCacheInfo(key,cache.getValue());
        return new JsonResult(0,cache);
    }

}
