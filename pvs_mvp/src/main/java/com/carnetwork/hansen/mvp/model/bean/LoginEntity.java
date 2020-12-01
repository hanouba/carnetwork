package com.carnetwork.hansen.mvp.model.bean;

/**
 * 登录提交的数据
 */
public class LoginEntity {
    /**
     * 验证码
     */
    private String code;

    /**
     * 手机号
     */
    private String phone;

    //    车队名称
    private String projectName;



    public LoginEntity(String phone, String code, String projectName) {

        this.phone = phone;
        this.code = code;
        this.projectName = projectName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
