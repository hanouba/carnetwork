package com.carnetwork.hansen.mvp.model.bean;

/**
 * @author HanN on 2020/12/7 18:45
 * @email: 1356548475@qq.com
 * @project carnetwork
 * @description:车辆创建
 * @updateuser:
 * @updatedata: 2020/12/7 18:45
 * @updateremark:
 * @version: 2.1.67
 */
public class CarCreateEnity {

    private String carLicence;
    private String carNo;
    private String phone;
    private String projectId;

    public CarCreateEnity(String carLicence, String carNo, String phone, String projectId) {
        this.carLicence = carLicence;
        this.carNo = carNo;
        this.phone = phone;
        this.projectId = projectId;
    }
}
