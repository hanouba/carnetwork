package com.carnetwork.hansen.mvp.model.bean;

public class LoginBean {

    /**
     * errorCode : string
     * errorMessage : string
     * errorTips : string
     * model : {}
     * success : true
     */


    private String model;
    private boolean success;
    private String errorMessage;
    private String errorCode;
    private String errorTips;


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public static class ModelBean {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorTips() {
        return errorTips;
    }

    public void setErrorTips(String errorTips) {
        this.errorTips = errorTips;
    }
}
