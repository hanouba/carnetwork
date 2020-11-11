package com.carnetwork.hansen.mvp.model.bean;

/**
 * 登录提交的数据
 */
public class LoginEntity {
    /**
     * 车辆编号
     */
    private String carNo;
    /**
     * 车牌号
     */
    private String carLicence;
    /**
     * 手机号
     */
    private String phone;
    //用户名
    private String name;
    //    车队名称
    private String motorcade;

    public LoginEntity(String carNo, String calLicence, String phone, String name) {
        this.carNo = carNo;
        this.carLicence = calLicence;
        this.phone = phone;
        this.name = name;
    }

    public LoginEntity(String phone, String name, String motorcade) {

        this.phone = phone;
        this.name = name;
        this.motorcade = motorcade;
    }

    public String getCarNo() {
        return carNo;
    }

    public String getCarLicence() {
        return carLicence;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }
}
