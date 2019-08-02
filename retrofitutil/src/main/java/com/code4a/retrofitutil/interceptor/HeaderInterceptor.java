package com.code4a.retrofitutil.interceptor;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Desc: 请求头拦截器
 * <p>
 * Create by sinochem on 2018/11/6
 * <p>
 * Version: 1.0.0
 */
public class HeaderInterceptor implements Interceptor {

    Map<String, String> httpHeaderMap;

    public HeaderInterceptor(Map<String, String> httpHeaderMap) {
        this.httpHeaderMap = httpHeaderMap;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        for (String key : httpHeaderMap.keySet()) {
            builder.addHeader(key, httpHeaderMap.get(key));
        }
        Request request = builder.build();
        return chain.proceed(request);
    }
}
