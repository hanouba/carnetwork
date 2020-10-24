package com.carnetwork.hansen.mvp.model.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 用户定位信息
 */
@Entity
public class UserLocationInfo {
    //主键自增长
    @Unique
    @Id(autoincrement = true)
    private Long id;
    //用户名
    private String userId;
    //    定位时间
    private String timeDate;
    //    定位时间毫秒值（用于比对删除）
    private long timeDateMilliseconds;
    //    纬度
    private double latitude;
    //    经度
    private double longitude;
    //    地理位置信息
    private String address;
    @Generated(hash = 1315900999)
    public UserLocationInfo(Long id, String userId, String timeDate,
            long timeDateMilliseconds, double latitude, double longitude,
            String address) {
        this.id = id;
        this.userId = userId;
        this.timeDate = timeDate;
        this.timeDateMilliseconds = timeDateMilliseconds;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }
    @Generated(hash = 443697694)
    public UserLocationInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getTimeDate() {
        return this.timeDate;
    }
    public void setTimeDate(String timeDate) {
        this.timeDate = timeDate;
    }
    public long getTimeDateMilliseconds() {
        return this.timeDateMilliseconds;
    }
    public void setTimeDateMilliseconds(long timeDateMilliseconds) {
        this.timeDateMilliseconds = timeDateMilliseconds;
    }
    public double getLatitude() {
        return this.latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return this.longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
