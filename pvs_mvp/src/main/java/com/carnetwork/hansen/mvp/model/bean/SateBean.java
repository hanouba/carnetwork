package com.carnetwork.hansen.mvp.model.bean;

import java.util.List;

/**
 * @author HanN on 2020/10/30 10:08
 * @email: 1356548475@qq.com
 * @project carnetwork
 * @description: 起点终点列表
 * @updateuser:
 * @updatedata: 2020/10/30 10:08
 * @updateremark:
 * @version: 2.1.67
 */
public class SateBean {


    private long id;
    private String sateName;
    private String lat;
    private String lon;
    private int sateType;
    private String createTime;
    private Object createUser;
    private int isDelete;
    private String projectId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSateName() {
        return sateName;
    }

    public void setSateName(String sateName) {
        this.sateName = sateName;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public int getSateType() {
        return sateType;
    }

    public void setSateType(int sateType) {
        this.sateType = sateType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Object getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Object createUser) {
        this.createUser = createUser;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
