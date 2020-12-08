package com.carnetwork.hansen.mvp.model.http.exception;

/**
 * author：Sun on 2017/8/25/0025.
 * email：1564063766@qq.com
 * remark:
 */
public class ApiException extends Exception{

    private int code;
    private String errorCode;

    public ApiException(String msg) {
        super(msg);
    }

    public ApiException(String msg, int code) {
        super(msg);
        this.code = code;
    }
    public ApiException(String msg, String errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
