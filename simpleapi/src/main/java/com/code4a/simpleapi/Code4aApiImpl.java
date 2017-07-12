package com.code4a.simpleapi;

import android.util.Log;
import com.code4a.retrofitutil.engine.RetrofitManager;
import com.code4a.retrofitutil.rx.RxHelper;
import com.code4a.retrofitutil.rx.RxSubscriber;
import com.code4a.retrofitutil.utils.HttpUtil;
import com.code4a.simpleapi.simple.ContentBoard;

import rx.Observer;
import rx.Subscription;

/**
 * Created by code4a on 2017/4/7.
 */

public class Code4aApiImpl implements Code4aApi {

    private static final String TAG = Code4aApiImpl.class.getSimpleName();

    RetrofitManager retrofitManager;

    public Code4aApiImpl() {
        retrofitManager = new RetrofitManager.Builder()
                .setBaseUrl(SimpleApi.CODE4A_API)
                .setTimeoutSec(15)
                .setTransferDataType(RetrofitManager.Builder.TransferDataType.GSON)
                .setHttpHeaderMap(HttpUtil.getJsonHeaderMap())
                .build();
    }

    Code4aService createCode4aService() {
        return retrofitManager.create(Code4aService.class);
    }

    @Override
    public Subscription testHttp() {
        return createCode4aService()
                .getAlipayInfo(CODE4A_ALIPAYINFO)
                .compose(RxHelper.<String>handleResult())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted ---> : xxxxxxxxxxxxxx ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError ---> : " + e);
                    }

                    @Override
                    public void onNext(String result) {
                        Log.e(TAG, "result : " + result);
                    }
                });
    }

    @Override
    public Subscription getContentBoard(RxSubscriber<ContentBoard> rxSubscriber) {
        return retrofitManager
                .doSubscribe(createCode4aService()
                .getContentBoard(CONTENT_BOARD), rxSubscriber);
    }

}
