package com.code4a.retrofitutil.cache;

import okhttp3.Cache;
import okhttp3.Interceptor;

/**
 * Created by jiang on 2018/5/21.
 */

public interface CacheConfig {

    /**
     * 获取缓存
     *
     * @return
     */
    Cache getCache();

    /**
     * 在线状态下缓存机制
     *
     * @return
     */
    Interceptor getOnlineCacheInterceptor();

    /**
     * 离线状态下缓存机制
     *
     * @return
     */
    Interceptor getOfflineCacheInterceptor();
}
