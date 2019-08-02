package com.code4a.retrofitutil.engine;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.code4a.retrofitutil.bean.RespBase;
import com.code4a.retrofitutil.cache.CacheConfig;
import com.code4a.retrofitutil.cache.CacheConfigHelper;
import com.code4a.retrofitutil.interceptor.HeaderInterceptor;
import com.code4a.retrofitutil.interceptor.IInterceptor;
import com.code4a.retrofitutil.interceptor.InterceptorHelper;
import com.code4a.retrofitutil.rx.RxHelper;
import com.code4a.retrofitutil.rx.RxSubscriber;
import com.code4a.retrofitutil.service.BaseService;
import com.code4a.retrofitutil.ssl.SSLHelper;
import com.code4a.retrofitutil.ssl.SSLInterface;
import com.code4a.retrofitutil.utils.HttpType;
import com.code4a.retrofitutil.utils.LogUtil;
import com.code4a.retrofitutil.utils.TransferDataType;
import com.facebook.stetho.Stetho;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by code4a on 2017/5/22.
 */

public final class RetrofitManager {

    Retrofit retrofit;

    RetrofitManager(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    /**
     * 初始化App听诊器
     *
     * @param context 上下文对象
     */
    public static void initStetho(Context context) {
        Stetho.initializeWithDefaults(context);
    }

    /**
     * 创建Api Service
     *
     * @param clazz Service的class
     * @param <S>   泛型类
     * @return 返回api service类
     */
    public <S> S create(Class<S> clazz) {
        return retrofit.create(clazz);
    }

    /**
     * 发送get请求，获取字符串，响应在主线程
     *
     * @param path       请求的二级路径
     * @param subscriber 订阅者
     */
    public void getStringRequest(String path, Observer<String> subscriber) {
        getStringRequest(path, new HashMap<String, Object>(), subscriber);
    }

    /**
     * 发送get请求，获取字符串，响应在主线程
     *
     * @param path       请求的二级路径
     * @param paramMap   请求参数
     * @param subscriber 订阅者
     */
    public void getStringRequest(String path, @NonNull Map<String, Object> paramMap, Observer<String> subscriber) {
        doSubscribe(create(BaseService.class).getStringRequest(path, paramMap), subscriber);
    }

    /**
     * 发送get请求，获取响应体，响应在IO线程
     *
     * @param path       请求的二级路径
     * @param subscriber 订阅者
     */
    public void getResponseBodyRequest(String path, Observer<ResponseBody> subscriber) {
        getResponseBodyRequest(path, new HashMap<String, Object>(), subscriber);
    }

    /**
     * 发送get请求，获取响应体，响应在IO线程
     *
     * @param path       请求的二级路径
     * @param paramMap   请求参数
     * @param subscriber 订阅者
     */
    public void getResponseBodyRequest(String path, @NonNull Map<String, Object> paramMap, Observer<ResponseBody> subscriber) {
        doIoSubscribe(create(BaseService.class).getResponseBodyRequest(path, paramMap), subscriber);
    }

    /**
     * 发送post请求，获取字符串，响应在主线程
     *
     * @param path        请求的二级路径
     * @param requestBody 请求体
     * @param subscriber  订阅者
     */
    public void postStringRequest(String path, RequestBody requestBody, Observer<String> subscriber) {
        postStringRequest(path, new HashMap<String, Object>(), requestBody, subscriber);
    }

    /**
     * 发送post请求，获取字符串，响应在主线程
     *
     * @param path        请求的二级路径
     * @param paramMap    请求参数
     * @param requestBody 请求体
     * @param subscriber  订阅者
     */
    public void postStringRequest(String path, @NonNull Map<String, Object> paramMap, RequestBody requestBody, Observer<String> subscriber) {
        doSubscribe(create(BaseService.class).postStringRequest(path, paramMap, requestBody), subscriber);
    }

    /**
     * 发送post请求，获取响应体，响应在IO线程
     *
     * @param path        请求的二级路径
     * @param requestBody 请求体
     * @param subscriber  订阅者
     */
    public void postResponseBodyRequest(String path, RequestBody requestBody, Observer<ResponseBody> subscriber) {
        postResponseBodyRequest(path, new HashMap<String, Object>(), requestBody, subscriber);
    }

    /**
     * 发送post请求，获取响应体，响应在IO线程
     *
     * @param path        请求的二级路径
     * @param paramMap    请求参数
     * @param requestBody 请求体
     * @param subscriber  订阅者
     */
    public void postResponseBodyRequest(String path, @NonNull Map<String, Object> paramMap, RequestBody requestBody, Observer<ResponseBody> subscriber) {
        doIoSubscribe(create(BaseService.class).postResponseBodyRequest(path, paramMap, requestBody), subscriber);
    }

    /**
     * 订阅事件，变换各自执行线程，请求在IO线程，响应在主线程
     *
     * @param observable 事件
     * @param subscriber 订阅者
     * @param <T>        泛型类
     */
    public <T> void doSubscribe(Observable<T> observable, Observer<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 订阅事件，变换各自执行线程，请求在IO线程，响应在IO线程
     *
     * @param observable 事件
     * @param subscriber 订阅者
     * @param <T>        泛型类
     */
    public <T> void doIoSubscribe(Observable<T> observable, Observer<T> subscriber) {
        observable.subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(subscriber);
    }

    /**
     * 变换各自执行线程，请求在IO线程，响应在主线程
     *
     * @param observable 事件
     * @param <T>        泛型类
     * @return 事件
     */
    public <T> Observable<T> doSubscribe(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 变换数据，并且订阅事件
     *
     * @param observable   事件
     * @param rxSubscriber 订阅者
     * @param <T>          泛型类
     */
    public <C, T> void doComposeSubscribe(Observable<? extends RespBase<C, T>> observable, RxSubscriber<T> rxSubscriber) {
        observable.compose(RxHelper.<C, T>handleResult()).subscribe(rxSubscriber);
    }

    /**
     * 类构造器
     */
    public static final class Builder {
        private Context context;
        private String baseUrl;
        private int timeoutSec = 30;
        private String[] hostnameVerifiers;
        private java.lang.String assetCertificateName;
        private HttpType httpType;
        private TransferDataType transferDataType;
        private SSLInterface sslInterface;
        private CacheConfig cacheConfig;
        private OkHttpConfig okHttpConfig;
        private IInterceptor iInterceptor;
        private boolean enableCache = false;

        /**
         * 设置上下文对象，用于获取证书流对象
         *
         * @param context 上下文对象
         * @return 构造器对象
         */
        public Builder setContext(@NonNull Context context) {
            this.context = context;
            return this;
        }

        /**
         * 设置请求的根地址
         *
         * @param baseUrl 请求根地址
         * @return 构造器对象
         */
        public Builder setBaseUrl(@NonNull String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * 设置OKHttp超时时间
         *
         * @param timeoutSec 超时时间，单位s
         * @return 构造器对象
         */
        public Builder setTimeoutSec(@NonNull int timeoutSec) {
            this.timeoutSec = timeoutSec;
            return this;
        }

        /**
         * 设置验证的host
         *
         * @param hostnameVerifiers host数组
         * @return 构造器对象
         */
        public Builder setHostnameVerifiers(@NonNull String[] hostnameVerifiers) {
            this.hostnameVerifiers = hostnameVerifiers;
            return this;
        }

        /**
         * 设置证书名称，证书需放在asset目录下
         *
         * @param assetCertificateName 证书名称
         * @return 构造器对象
         */
        public Builder setAssetCertificateName(@NonNull String assetCertificateName) {
            this.assetCertificateName = assetCertificateName;
            return this;
        }

        /**
         * 设置请求头
         *
         * @param httpHeaderMap 请求头
         * @return 构造器对象
         */
        public Builder setHttpHeaderMap(@NonNull Map<String, String> httpHeaderMap) {
            if (httpHeaderMap != null) {
                addInterceptor(new HeaderInterceptor(httpHeaderMap));
            }
            return this;
        }

        /**
         * 设置http类型，http/https
         *
         * @param httpType 请求类型
         * @return 构造器对象
         */
        public Builder setHttpType(@NonNull HttpType httpType) {
            this.httpType = httpType;
            return this;
        }

        /**
         * 设置响应数据类型 string/json/default
         *
         * @param transferDataType 数据类型
         * @return 构造器对象
         */
        public Builder setTransferDataType(@NonNull TransferDataType transferDataType) {
            this.transferDataType = transferDataType;
            return this;
        }

        /**
         * 设置ssl, 可继承SSLHelper类，实现ssl的校验操作等
         *
         * @param sslInterface ssl帮助类
         * @return 构造器对象
         */
        public Builder setSSLHelper(SSLInterface sslInterface) {
            this.sslInterface = sslInterface;
            return this;
        }

        /**
         * 设置拦截器帮助类
         *
         * @param iInterceptor 拦截器
         * @return 构造器对象
         */
        public Builder setIInterceptor(IInterceptor iInterceptor) {
            this.iInterceptor = iInterceptor;
            return this;
        }

        /**
         * 启用log
         *
         * @param tag log标签
         * @return 构造器对象
         */
        public Builder enableLogging(final String tag) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    LogUtil.d((TextUtils.isEmpty(tag) ? "RM:" : tag) + message);
                }
            });
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            addInterceptor(loggingInterceptor);
            return this;
        }

        /**
         * 添加拦截器
         *
         * @param interceptor 拦截器
         * @return 构造器对象
         */
        public Builder addInterceptor(Interceptor interceptor) {
            if (this.iInterceptor == null) {
                iInterceptor = new InterceptorHelper();
            }
            iInterceptor.addInterceptors(interceptor);
            return this;
        }

        /**
         * 开启缓存
         *
         * @param cacheConfig 缓存机制
         * @return 构造器对象
         */
        public Builder addCache(CacheConfig cacheConfig) {
            this.enableCache = true;
            this.cacheConfig = cacheConfig;
            return this;
        }

        /**
         * 自定义配置okhttpclient
         *
         * @param okHttpConfig 配置接口
         * @return 构造器
         */
        public Builder configOkHttpClient(OkHttpConfig okHttpConfig) {
            this.okHttpConfig = okHttpConfig;
            return this;
        }

        /**
         * 开启缓存功能，并使用默认缓存机制
         *
         * @return 构造器对象
         */
        public Builder enableCache() {
            this.enableCache = true;
            return this;
        }

        public RetrofitManager build() {
            checkParams();
            Retrofit retrofit = null;
            OkHttpClient.Builder builder = null;
            if (iInterceptor == null) {
                iInterceptor = new InterceptorHelper();
            }
            OkHttpClientEngine okHttpClientEngine = new OkHttpClientEngine(iInterceptor);
            if (httpType == null) {
                httpType = HttpType.HTTP;
            }
            if (httpType == HttpType.HTTP) {
                if (enableCache) {
                    if (cacheConfig == null) {
                        cacheConfig = new CacheConfigHelper(context);
                    }
                    builder = okHttpClientEngine.defaultOkHttpClient(cacheConfig, timeoutSec);
                } else {
                    builder = okHttpClientEngine.defaultOkHttpClient(timeoutSec);
                }
            } else if (httpType == HttpType.HTTPS) {
                if (sslInterface == null) {
                    sslInterface = new SSLHelper(context, hostnameVerifiers, assetCertificateName);
                }
                if (enableCache) {
                    if (cacheConfig == null) {
                        cacheConfig = new CacheConfigHelper(context);
                    }
                    builder = okHttpClientEngine.httpsOkHttpClient(sslInterface, cacheConfig, timeoutSec);
                } else {
                    builder = okHttpClientEngine.httpsOkHttpClient(sslInterface, timeoutSec);
                }
            }
            if (okHttpConfig != null) {
                okHttpConfig.configOkHttpClient(builder);
            }
            if (transferDataType == null) {
                transferDataType = TransferDataType.DEFAULT;
            }
            if (transferDataType == TransferDataType.DEFAULT) {
                retrofit = RetrofitEngine.getDefaultRetrofit(baseUrl, builder);
            } else if (transferDataType == TransferDataType.STRING) {
                retrofit = RetrofitEngine.getScalarsConverterRetrofit(baseUrl, builder);
            } else if (transferDataType == TransferDataType.JSON) {
                retrofit = RetrofitEngine.getGsonConverterRetrofit(baseUrl, builder);
            }
            RetrofitManager retrofitManager = new RetrofitManager(retrofit);
            return retrofitManager;
        }

        private void checkParams() {
            if (TextUtils.isEmpty(baseUrl)) {
                throw new RuntimeException("Please call setBaseUrl method, baseUrl can't be null");
            }
            if (httpType != null && httpType == HttpType.HTTPS && sslInterface == null) {
                if (context == null) {
                    throw new RuntimeException("Please call setContext method, context can't be null");
                }
                if (TextUtils.isEmpty(assetCertificateName)) {
                    throw new RuntimeException("Please call setAssetCertificateName method, assetCertificateName can't be null");
                }
                if (hostnameVerifiers == null) {
                    throw new RuntimeException("Please call setHostnameVerifiers method, hostnameVerifiers can't be null");
                }
            } else if (enableCache) {
                if (context == null) {
                    throw new RuntimeException("Please call setContext method, context can't be null");
                }
            }
        }
    }

}
