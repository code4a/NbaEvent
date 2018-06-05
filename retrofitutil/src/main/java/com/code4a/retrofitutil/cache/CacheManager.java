package com.code4a.retrofitutil.cache;

import android.content.Context;
import android.text.TextUtils;

import com.code4a.retrofitutil.utils.NetUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jiang on 2018/5/21.
 */

public class CacheManager {

    /**
     * 默认缓存目录
     */
    static final String DEFAULT_CACHE_DIR = "code4aCache";
    /**
     * 默认缓存大小
     */
    static final long DEFAULT_CACHE_MAX_SIZE = 10 * 1024 * 1024;
    /**
     * 默认缓存时长
     */
    static final int DEFALUT_CACHE_MAX_STATE = 30;
    /**
     * 无网络,设缓存有效期为两周
     */
    static final long CACHE_STALE_SEC = 60 * 60 * 24 * 14;
    /**
     * 有网 缓存60s
     */
    static final long MAX_AGE = 60;

    /**
     * 获取缓存
     *
     * @param context
     * @param cacheDir     缓存目录的下级目录名称
     * @param cacheMaxSize 缓存的大小
     * @return
     */
    public static Cache getCache(Context context, String cacheDir, long cacheMaxSize) {
        File cacheFile = new File(context.getCacheDir(), cacheDir);
        Cache cache = new Cache(cacheFile, cacheMaxSize);
        return cache;
    }

    /**
     * 获取默认的缓存，目录在应用的cache目录下的code4aCache目录里
     *
     * @param context
     * @return
     */
    public static Cache getCache(Context context) {
        return getCache(context, DEFAULT_CACHE_DIR, DEFAULT_CACHE_MAX_SIZE);
    }

    /**
     * 未联网获取缓存数据
     *
     * @param context
     * @param cacheMaxState 缓存时长，单位day
     * @return
     */
    public static Interceptor getOfflineCacheInterceptor(final Context context, final int cacheMaxState) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetUtil.isNetworkAvailable(context)) {
                    //在20秒缓存有效，此处测试用，实际根据需求设置具体缓存有效时间
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(cacheMaxState, TimeUnit.DAYS)
                            .build();
                    request = request.newBuilder()
                            .cacheControl(cacheControl)
                            .build();
                }
                return chain.proceed(request);
            }
        };
    }

    /**
     * 获取离线的缓存拦截器
     *
     * @param context
     * @return
     */
    public static Interceptor getOfflineCacheInterceptor(Context context) {
        return getOfflineCacheInterceptor(context, DEFALUT_CACHE_MAX_STATE);
    }

    public static Interceptor reWriteCacheInterceptor(final Context context) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                String cacheControl = request.cacheControl().toString();
                if (!NetUtil.isNetworkAvailable(context)) {
                    request = request.newBuilder()
                            .cacheControl(TextUtils.isEmpty(cacheControl) ? CacheControl.FORCE_CACHE :
                                    CacheControl.FORCE_NETWORK)
                            .build();
                }
                Response originalResponse = chain.proceed(request);
                if (NetUtil.isNetworkAvailable(context)) {
                    //有网的时候连接服务器请求,缓存60s
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, max-age=" + MAX_AGE)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    //网络断开时读取缓存
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                            .removeHeader("Pragma")
                            .build();
                }
            }
        };
    }

    public static Interceptor provideCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                // re-write response header to force use of cache
                // 正常访问同一请求接口（多次访问同一接口），给30秒缓存，超过时间重新发送请求，否则取缓存数据
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(3, TimeUnit.SECONDS)
                        .build();
                return response.newBuilder()
                        .header("Cache-Control", cacheControl.toString())
                        .build();
            }
        };
    }

}
