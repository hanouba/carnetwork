package com.carnetwork.hansen.mvp.model.bean;

/**
 * @author HanN on 2020/12/1 10:41
 * @email: 1356548475@qq.com
 * @project carnetwork
 * @description: 登录成功 获取token
 * @updateuser:
 * @updatedata: 2020/12/1 10:41
 * @updateremark:
 * @version: 2.1.67
 */
public class LoginV2 {



    public static class ModelBean {
        /**
         * name : 111111
         * userId : 87250401186942976
         * projectId : 87250401174360064
         * phone : 17671756010
         * code : 1222
         * carNo : null
         * carLicence : null
         * roleType : 1
         * token : bbbbe31e631fe4926c69171571c0baac
         */

        private String name;
        private long userId;
        private long projectId;
        private String phone;
        private String code;
        private String carNo;
        private String carLicence;
        private int roleType;
        private String token;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public long getProjectId() {
            return projectId;
        }

        public void setProjectId(long projectId) {
            this.projectId = projectId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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

        public int getRoleType() {
            return roleType;
        }

        public void setRoleType(int roleType) {
            this.roleType = roleType;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
