package com.code4a.simpleapi;

import com.code4a.retrofitutil.bean.RespBase;
import com.code4a.simpleapi.simple.ContentBoard;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by code4a on 2017/4/7.
 */

public interface Code4aService {

    @GET("{key}")
    Observable<RespBase<String, String>> getAlipayInfo(
            @Path("key") String key
    );

    @GET(SimpleApi.SIMPLE_PLATFORMURL + "{key}")
    Observable<ContentBoard> getContentBoard(
            @Path("key") String key
    );

}
