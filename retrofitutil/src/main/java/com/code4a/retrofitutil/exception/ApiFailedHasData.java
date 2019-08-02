package com.code4a.retrofitutil.exception;

/**
 * Created by code4a on 2017/5/26.
 */

public class ApiFailedHasData extends RuntimeException {

    private String code;
    private Object obj;

    public ApiFailedHasData(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
