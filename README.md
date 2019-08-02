# RetrofitUtil
> Retrofit + OKHttp + RxJava 简单二次封装

### 如何使用

1.在项目Module的`build.gradle`中添加依赖

`compile 'com.code4a:retrofit-util:1.2.0'`

2.创建RetrofitManager对象

```
RetrofitManager retrofitManager;

public Code4aApiImpl() {
    retrofitManager = new RetrofitManager.Builder()
            .setContext(context)
            .setBaseUrl(SimpleApi.CODE4A_API)
            .enableCache()
            .setTimeoutSec(15)
            .setTransferDataType(RetrofitManager.Builder.TransferDataType.GSON)
            .setHttpHeaderMap(HttpUtil.getJsonHeaderMap())
            .build();
}
```

3.Api简述

* 创建ApiService

    `public <S> S create(Class<S> clazz)`
    
* 订阅事件

    `public <T> Subscription doSubscribe(Observable<T> observable, Subscriber<T> subscriber)`
    
4.eg

* http get请求获取字符串，响应在主线程中

```
void httpGetString(){
    new RetrofitManager.Builder()
            .setBaseUrl("http://www.code4a.com/")
            .setTransferDataType(RetrofitManager.Builder.TransferDataType.STRING)
            .build()
            .getStringRequest("android", new Subscriber<String>() {
                @Override
                public void onCompleted() {
                    
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(String s) {

                }
            });
}
```

* http get请求获取ResponseBody，响应在IO线程中，可进行下载等操作

```
void httpGetResponseBody(){
    new RetrofitManager.Builder()
            .setBaseUrl("http://www.code4a.com/")
            .build()
            .getResponseBodyRequest("android", new Subscriber<ResponseBody>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(ResponseBody body) {

                }
            });
}
```

* 自定义Service 进行json数据类型传输，自动转换bean对象

     1.Service类
     
    ```
    @GET("{type}/{size}/{page}")
    Observable<GankIoBean> getBeanRequest(
            @Path(value = "type", encoded = true) String type,
            @Path(value = "size", encoded = true) int size,
            @Path(value = "page", encoded = true) int page
    );
    ```
    
    2.封装转换
    
    ```
    GankIoService getService() {
        return retrofitManager.create(GankIoService.class);
    }

    public Observable<GankIoBean> getResource(String type, int page, int size) {
        return getService().getBeanRequest(type, size, page);
    }

    public Subscription getResourceDoSubcribe(String type, int page, int size, Subscriber<GankIoBean> subscriber) {
        return retrofitManager.doSubscribe(getResource(type, page, size), subscriber);
    }
    ```
    
    3.获取结果
    
    ```
    protected void loadData(String type, int page, int size) {
        unsubscribe();
        subscription = getGankIoManager().getResourceDoSubcribe(type, page, size, new Subscriber<GankIoBean>() {
            @Override
            public void onCompleted() {
                onLoadDataCompleted();
            }

            @Override
            public void onError(Throwable e) {
                onLoadDataError();
            }

            @Override
            public void onNext(GankIoBean gankIoBean) {
                if (gankIoBean.isError()) {
                    onLoadDataFailed();
                } else {
                    onLoadDataSuccess(gankIoBean.getResults());
                }
            }
        });
    ```

详细用法可参看simpleapi

#### 还可用作HTTPS请求，可用默认SSLHelper，未校验证书，可以实现SSLInterface，通过`setSSLHelper(SSLInterface sslHelper)`实现自定义ssl规则

1.创建可进行https操作的RetrofitManager

```
RetrofitManager retrofitManager;

    AndLinkUserNetImpl() {
        retrofitManager = new RetrofitManager.Builder()
                .setBaseUrl(AndLinkApi.ANDLINK_USER_ROOTURL)
                .setTimeoutSec(15)
                .setTransferDataType(RetrofitManager.Builder.TransferDataType.GSON)
                .setHttpHeaderMap(HttpUtil.getJsonHeaderMap())
                .setHttpType(RetrofitManager.Builder.HttpType.HTTPS)
                .setSSLHelper(new SSLImpl())
                .build();
//        使用自定义证书校验的https请求
//        retrofitManager = new RetrofitManager.Builder()
//                .setBaseUrl(AndLinkApi.ANDLINK_USER_ROOTURL)
//                .setContext(context)
//                .setTimeoutSec(15)
//                .setTransferDataType(RetrofitManager.Builder.TransferDataType.GSON)
//                .setHttpHeaderMap(HttpUtil.getJsonHeaderMap())
//                .setHostnameVerifiers(AndLinkApi.HOSTURLS)
//                .setAssetCertificateName(AndLinkApi.CERTIFICATE_NAME)
//                .setHttpType(RetrofitManager.Builder.HttpType.HTTPS)
//                .build();
    }
```

2.请求接口定义

```
@POST(AndLinkApi.ANDLINK_PLATFORMURL + "user/login")
Observable<BaseResponseBean<ResponseLoginUserBean>> aluLogin(@Body RequestLoginUserBean requestLoginUserBean);
```

3.请求封装和转换

```
@Override
public Subscription andLinkUser_Login(RequestLoginUserBean requestLoginUserBean, RxSubscriber<ResponseLoginUserBean> rxSubscriber) {
    return createUserService()
            .aluLogin(requestLoginUserBean)
            .compose(RxHelper.<ResponseLoginUserBean>handleResult())
            .subscribe(rxSubscriber);
}
```

### 致谢

* [代码家](https://github.com/daimajia)的[干货集中营 API 文档](http://gank.io/api)