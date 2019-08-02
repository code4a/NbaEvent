package com.code4a.retrofitutil.interceptor;

import java.util.List;

import okhttp3.Interceptor;

/**
 * Desc: 拦截器接口
 * <p>
 * Create by sinochem on 2018/11/6
 * <p>
 * Version: 1.0.0
 */
public interface IInterceptor {

    /**
     * 获取所有拦截器
     *
     * @return 拦截器集合
     */
    List<Interceptor> getInterceptors();

    /**
     * 添加拦截器
     *
     * @param interceptor 拦截器
     * @return 当前类对象
     */
    IInterceptor addInterceptors(Interceptor interceptor);

}
