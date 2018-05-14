package com.zx.system.cache;

import lombok.Data;

@Data
public class Cache {

    //缓存ID
    private String key;
    //缓存数据
    private Object value;
    //更新时间
    private long timeOut;

    public Cache() {
        super();
        setTimeOut(System.currentTimeMillis() + 1000 * 3);
    }

    public Cache(String key, Object value) {
        this.key = key;
        this.value = value;
        this.timeOut = System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 3;//默认缓存
    }

    public Cache(String key, Object value, long timeOut) {
        this.key = key;
        this.value = value;
        this.timeOut = timeOut + System.currentTimeMillis();
    }


}
