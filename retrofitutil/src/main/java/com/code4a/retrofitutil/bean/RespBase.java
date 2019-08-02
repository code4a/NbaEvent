package com.code4a.retrofitutil.bean;

/**
 * Created by code4a on 2017/5/27.
 * 响应体的基类
 *
 * @param <C> the response code type
 * @param <T> the response date type
 */
public class RespBase<C, T> {

    private C code;
    private T data;

    public C getCode() {
        return code;
    }

    public void setCode(C code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
