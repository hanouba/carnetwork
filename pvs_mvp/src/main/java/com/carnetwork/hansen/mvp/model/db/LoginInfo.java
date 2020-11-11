package com.carnetwork.hansen.mvp.model.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 创建者 by ${HanSir} on 2018/11/21.
 * 版权所有  WELLTRANS.
 * 说明
 */
@Entity
public class LoginInfo {
    //主键自增长
    @Id(autoincrement = true)
    private Long id;
    //用户名
    @Unique
    private String username;
    //车辆编号
    private String carNo;
    //车牌号
    private String carLicence;
    //手机号
    private String phone;
    //项目名称
    private String projectName;

    private String token;

    public LoginInfo(String username, String carNo ,String carLicence,String phone,String token) {
        this.username = username;
        this.carNo = carNo;
        this.carLicence = carLicence;
        this.phone = phone;
        this.token = token;
    }

    /**
     *
     * @param username
     * @param phone
     * @param projectName
     */
    public LoginInfo(String username,String phone,String projectName) {
        this.username = username;
        this.phone = phone;
        this.projectName = projectName;
    }
    public LoginInfo(String projectName,String username, String carNo ,String carLicence,String phone,String token) {
        this.projectName = projectName;
        this.username = username;
        this.carNo = carNo;
        this.carLicence = carLicence;
        this.phone = phone;
        this.token = token;
    }


    @Generated(hash = 682191453)
    public LoginInfo(Long id, String username, String carNo, String carLicence, String phone, String projectName,
            String token) {
        this.id = id;
        this.username = username;
        this.carNo = carNo;
        this.carLicence = carLicence;
        this.phone = phone;
        this.projectName = projectName;
        this.token = token;
    }

    @Generated(hash = 1911824992)
    public LoginInfo() {
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getUsername() {
        return this.username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getCarNo() {
        return this.carNo;
    }


    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }


    public String getCarLicence() {
        return this.carLicence;
    }


    public void setCarLicence(String carLicence) {
        this.carLicence = carLicence;
    }


    public String getPhone() {
        return this.phone;
    }


    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getToken() {
        return this.token;
    }


    public void setToken(String token) {
        this.token = token;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }



}
