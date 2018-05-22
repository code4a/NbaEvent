package com.code4a.simpleapi;

import android.content.Context;

import com.code4a.retrofitutil.engine.RetrofitManager;

/**
 * Created by code4a on 2017/7/12.
 */

public final class SimpleManager {

    static Code4aApi code4aApi;

    public static void init(Context mContext) {
        RetrofitManager.initStetho(mContext);
        code4aApi = new Code4aApiImpl(mContext);
    }

    public static Code4aApi getCode4aApi() {
        return code4aApi;
    }
}
