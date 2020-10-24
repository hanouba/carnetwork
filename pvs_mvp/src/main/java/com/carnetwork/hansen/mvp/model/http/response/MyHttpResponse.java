package com.carnetwork.hansen.mvp.model.http.response;

/**
 * author：Sun on 2017/8/25/0025.
 * email：1564063766@qq.com
 * remark:
 */
public class MyHttpResponse<T> {

    private T data;
    private String msg;
    private String id;
    private String result;
    private String errorCode;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "MyHttpResponse{" +
                "data=" + data +
                ", msg='" + msg + '\'' +
                ", id='" + id + '\'' +
                ", result='" + result + '\'' +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }
}
