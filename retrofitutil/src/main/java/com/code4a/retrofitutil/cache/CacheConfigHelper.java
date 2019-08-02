package com.code4a.retrofitutil.cache;

import android.content.Context;

import okhttp3.Cache;
import okhttp3.Interceptor;

/**
 * Created by jiang on 2018/5/21.
 */

public class CacheConfigHelper implements CacheConfig {

    private Context context;

    public CacheConfigHelper(Context context) {
        this.context = context;
    }

    /**
     * 获取缓存
     *
     * @return 缓存类
     */
    @Override
    public Cache getCache() {
        return CacheManager.getCache(context);
    }

    /**
     * 在线状态下缓存机制
     *
     * @return 拦截器
     */
    @Override
    public Interceptor getOnlineCacheInterceptor() {
        return CacheManager.reWriteCacheInterceptor(context);
    }

    /**
     * 离线状态下缓存机制
     *
     * @return 拦截器
     */
    @Override
    public Interceptor getOfflineCacheInterceptor() {
        return CacheManager.reWriteCacheInterceptor(context);
    }
}
