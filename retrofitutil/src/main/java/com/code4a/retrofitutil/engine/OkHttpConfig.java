package com.code4a.retrofitutil.engine;

import okhttp3.OkHttpClient;

/**
 * Desc: okhttp client 配置接口
 * <p>
 *
 * @author Create by sinochem on 2019-08-02
 * <p>
 * Version: 1.0.0
 */
public interface OkHttpConfig {

    /**
     * 自定义拓展okhttpclient
     *
     * @param builder 构造器
     */
    void configOkHttpClient(OkHttpClient.Builder builder);
}
