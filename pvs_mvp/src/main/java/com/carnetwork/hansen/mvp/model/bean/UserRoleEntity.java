package com.carnetwork.hansen.mvp.model.bean;

/**
 * @author HanN on 2021/1/15 13:36
 * @email: 1356548475@qq.com
 * @project carnetwork
 * @description:  用户权限
 * @updateuser:
 * @updatedata: 2021/1/15 13:36
 * @updateremark:
 * @version: 2.1.67
 */
public class UserRoleEntity {
    /**
     * 用户权限 1 管理员 其他司机
     */
    public String  roleType ;
    /**
     * 1 在线 其他离线
     */
    public String  status ;
    public String  userId ;

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
