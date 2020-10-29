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


        /**
         * id : 74917678061588480
         * lat : 116.377823000000000
         * lon : 39.987725000000000
         * 	"reportTime": "2020-10-29 15:18:22",
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
        private String reportTime;
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

        public String getReportTime() {
            return reportTime;
        }

        public void setReportTime(String reportTime) {
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
