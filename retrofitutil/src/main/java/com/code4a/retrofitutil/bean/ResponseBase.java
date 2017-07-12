package com.code4a.retrofitutil.bean;

/**
 * Created by code4a on 2017/5/27.
 */

public class ResponseBase<T> {

    private String code;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
