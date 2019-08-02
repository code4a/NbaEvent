package com.code4a.retrofitutil.exception;

/**
 * Created by code4a on 2017/5/26.
 */

public class ResponseCodeTypeException extends RuntimeException {

    private String message;

    public ResponseCodeTypeException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
