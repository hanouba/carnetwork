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
    private String success;


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String isSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public static class ModelBean {
    }
}
