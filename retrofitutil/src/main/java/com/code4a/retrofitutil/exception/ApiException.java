package com.code4a.retrofitutil.exception;

/**
 * Created by code4a on 2017/5/26.
 */

public class ApiException extends RuntimeException {

    private String code;

    public ApiException(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
