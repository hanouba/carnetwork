package com.carnetwork.hansen.mvp.model.bean;

/**
 * @author HanN on 2020/11/27 10:22
 * @email: 1356548475@qq.com
 * @project carnetwork
 * @description: 车队entity   {
 *   "code": "9629",
 *   "phone": "17671756010",
 *   "projectName": "258车队",
 *   "userName": "张三"
 * }
 * @updateuser:
 * @updatedata: 2020/11/27 10:22
 * @updateremark:
 * @version: 2.1.67
 */
public class ProjectEntity {
    private String code;
    private String phone;
    private String projectName;
    private String userName;

    public ProjectEntity(String code, String phone, String projectName, String userName) {
        this.code = code;
        this.phone = phone;
        this.projectName = projectName;
        this.userName = userName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
