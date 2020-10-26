package com.carnetwork.hansen.mvp.model.bean;

public class LoginEntity {
    private String carNo;
    private String carLicence;
    private String phone;
    private String name;

    public LoginEntity(String carNo, String calLicence, String phone, String name) {
        this.carNo = carNo;
        this.carLicence = calLicence;
        this.phone = phone;
        this.name = name;
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
