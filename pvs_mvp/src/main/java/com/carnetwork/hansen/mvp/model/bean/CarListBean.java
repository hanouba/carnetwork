package com.carnetwork.hansen.mvp.model.bean;

import java.util.List;

/**
 * 車輛列表
 */
public class CarListBean {



        /**
         * id : 90675449423859710
         * carNo : 009
         * carLicence : 鄂A000009
         * projectId : 90665069377097730
         * isDeleted : 0
         */

        private long id;
        private String carNo;
        private String carLicence;
        private String projectId;
        private int isDeleted;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getCarNo() {
            return carNo;
        }

        public void setCarNo(String carNo) {
            this.carNo = carNo;
        }

        public String getCarLicence() {
            return carLicence;
        }

        public void setCarLicence(String carLicence) {
            this.carLicence = carLicence;
        }

        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        public int getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(int isDeleted) {
            this.isDeleted = isDeleted;
        }
}
