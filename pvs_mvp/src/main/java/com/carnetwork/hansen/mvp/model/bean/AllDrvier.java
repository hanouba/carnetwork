package com.carnetwork.hansen.mvp.model.bean;

import java.util.List;

public class AllDrvier {

    /**
     * success : true
     * errorCode : null
     * errorMessage : null
     * errorTips : null
     * model : [{"id":74917678061588480,"lat":"116.377823000000000","lon":"39.987725000000000","reportTime":1603506035000,"carNum":"001","speed":"0.00km/h","direction":"-0.0","status":"stop","projectId":"1234568","name":"string","phone":"string"},{"id":73929847138816000,"lat":"116.377823000000000","lon":"39.987725000000000","reportTime":1603270518000,"carNum":"string","speed":"84.64km/h","direction":"-152.33690096132727","status":"run","projectId":"1234568","name":"string","phone":"string"}]
     */


    /**
     * carNum : 001
     * <p>
     * name : string
     * phone : string
     */


    private String carLicence;
    private String carNo;
    private String name;
    private String phone;
    private String onlineStatus;
    private String createTime;
    private String projectId;

    public String getCarLicence() {
        return carLicence;
    }

    public void setCarLicence(String carLicence) {
        this.carLicence = carLicence;
    }

    public String getCarNum() {
        return carNo;
    }

    public void setCarNum(String carNum) {
        this.carNo = carNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
