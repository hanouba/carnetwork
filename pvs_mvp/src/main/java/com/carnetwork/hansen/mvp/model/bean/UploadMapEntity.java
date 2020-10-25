package com.carnetwork.hansen.mvp.model.bean;

public class UploadMapEntity {
    private String carNo;
    private String lat;
    private String lon;
    private String name;
    private String phone;

    public UploadMapEntity(String carNo, String lat, String lon, String name, String phone) {
        this.carNo = carNo;
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        this.phone = phone;
    }
}
