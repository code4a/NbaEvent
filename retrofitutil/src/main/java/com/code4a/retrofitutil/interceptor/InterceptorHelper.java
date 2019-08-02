package com.code4a.retrofitutil.interceptor;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;

/**
 * Desc: 拦截器帮助类
 * <p>
 * Create by sinochem on 2018/11/6
 * <p>
 * Version: 1.0.0
 */
public class InterceptorHelper implements IInterceptor {

    List<Interceptor> interceptors = new ArrayList<>();

    @Override
    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    @Override
    public InterceptorHelper addInterceptors(Interceptor interceptor) {
        if (interceptor != null) {
            interceptors.add(interceptor);
        }
        return this;
    }
}
