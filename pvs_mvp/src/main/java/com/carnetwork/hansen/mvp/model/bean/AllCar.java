package com.carnetwork.hansen.mvp.model.bean;

import java.util.List;

public class AllCar {

    /**
     * success : true
     * errorCode : null
     * errorMessage : null
     * errorTips : null
     * model : [{"id":74917678061588480,"lat":"116.377823000000000","lon":"39.987725000000000","reportTime":1603506035000,"carNum":"001","speed":"0.00km/h","direction":"-0.0","status":"stop","projectId":"1234568","name":"string","phone":"string"},{"id":73929847138816000,"lat":"116.377823000000000","lon":"39.987725000000000","reportTime":1603270518000,"carNum":"string","speed":"84.64km/h","direction":"-152.33690096132727","status":"run","projectId":"1234568","name":"string","phone":"string"}]
     */

    private boolean success;
    private String errorCode;
    private String errorMessage;
    private String errorTips;
    private List<ModelBean> model;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getErrorTips() {
        return errorTips;
    }

    public void setErrorTips(String errorTips) {
        this.errorTips = errorTips;
    }

    public List<ModelBean> getModel() {
        return model;
    }

    public void setModel(List<ModelBean> model) {
        this.model = model;
    }

    public static class ModelBean {
        /**
         * id : 74917678061588480
         * lat : 116.377823000000000
         * lon : 39.987725000000000
         * reportTime : 1603506035000
         * carNum : 001
         * speed : 0.00km/h
         * direction : -0.0
         * status : stop
         * projectId : 1234568
         * name : string
         * phone : string
         */

        private long id;
        private String lat;
        private String lon;
        private long reportTime;
        private String carNum;
        private String speed;
        private String direction;
        private String status;
        private String projectId;
        private String name;
        private String phone;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
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

        public long getReportTime() {
            return reportTime;
        }

        public void setReportTime(long reportTime) {
            this.reportTime = reportTime;
        }

        public String getCarNum() {
            return carNum;
        }

        public void setCarNum(String carNum) {
            this.carNum = carNum;
        }

        public String getSpeed() {
            return speed;
        }

        public void setSpeed(String speed) {
            this.speed = speed;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
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
    }

    @Override
    public String toString() {
        return "AllCar{" +
                "success=" + success +
                ", errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", errorTips='" + errorTips + '\'' +
                ", model=" + model +
                '}';
    }
}
