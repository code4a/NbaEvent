package com.code4a.retrofitutil.engine;

import com.code4a.retrofitutil.cache.CacheConfig;
import com.code4a.retrofitutil.interceptor.IInterceptor;
import com.code4a.retrofitutil.ssl.SSLInterface;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * Created by code4a on 2017/5/22.
 */

class OkHttpClientEngine {

    IInterceptor iInterceptor;

    OkHttpClientEngine(IInterceptor iInterceptor) {
        this.iInterceptor = iInterceptor;
    }

    OkHttpClient.Builder httpsOkHttpClient(SSLInterface sslHelper, CacheConfig cacheConfig, int timeoutSec) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addNetworkInterceptor(new StethoInterceptor())
                .connectTimeout(timeoutSec, TimeUnit.SECONDS)
                .writeTimeout(timeoutSec, TimeUnit.SECONDS)
                .readTimeout(timeoutSec, TimeUnit.SECONDS);
        if (sslHelper != null) {
            builder.hostnameVerifier(sslHelper.getHostnameVerifier())
                    .sslSocketFactory(sslHelper.getSSLCertification(), sslHelper.getX509TrustManager());
        }
        if (cacheConfig != null) {
            builder.cache(cacheConfig.getCache())
                    .addInterceptor(cacheConfig.getOfflineCacheInterceptor())
                    .addNetworkInterceptor(cacheConfig.getOnlineCacheInterceptor());
        }
        if (iInterceptor != null
                && iInterceptor.getInterceptors() != null
                && iInterceptor.getInterceptors().size() > 0) {
            for (Interceptor interceptor : iInterceptor.getInterceptors()) {
                builder.addInterceptor(interceptor);
            }
        }
        return builder;
    }

    OkHttpClient.Builder httpsOkHttpClient(SSLInterface sslHelper, int timeoutSec) {
        return httpsOkHttpClient(sslHelper, null, timeoutSec);
    }

    OkHttpClient.Builder defaultOkHttpClient(CacheConfig cacheConfig, int timeoutSec) {
        return httpsOkHttpClient(null, cacheConfig, timeoutSec);
    }

    OkHttpClient.Builder defaultOkHttpClient(int timeoutSec) {
        return defaultOkHttpClient(null, timeoutSec);
    }

}
