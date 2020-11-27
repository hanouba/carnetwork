package com.carnetwork.hansen.mvp.model.bean;

/**
 * 登录提交的数据
 */
public class LoginEntity {
    /**
     * 验证码
     */
    private String verification;

    /**
     * 手机号
     */
    private String phone;

    //    车队名称
    private String motorcade;



    public LoginEntity(String phone, String verification, String motorcade) {

        this.phone = phone;
        this.verification = verification;
        this.motorcade = motorcade;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMotorcade() {
        return motorcade;
    }

    public void setMotorcade(String motorcade) {
        this.motorcade = motorcade;
    }
}
