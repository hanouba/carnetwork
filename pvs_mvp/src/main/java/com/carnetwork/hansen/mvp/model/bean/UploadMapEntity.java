package com.carnetwork.hansen.mvp.model.bean;

public class UploadMapEntity {
    private String carNo;
    private String lat;
    private String lon;
//    2020-10-30 14:05:29
    private String reportTime;


    public UploadMapEntity(String carNo, String lat, String lon, String reportTime) {
        this.carNo = carNo;
        this.lat = lat;
        this.lon = lon;
        this.reportTime = reportTime;
    }
}
