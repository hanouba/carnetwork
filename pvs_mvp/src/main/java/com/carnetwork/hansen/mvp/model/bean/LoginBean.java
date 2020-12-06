package com.carnetwork.hansen.mvp.model.bean;

public class LoginBean {


    /**
     * success : true
     * errorCode :
     * errorMessage :
     * errorTips :
     * model : {"name":"999","userId":90665069410652160,"projectId":90665069377097730,
     * "phone":"17671756010","code":"3516","carNo":"","carLicence":null,"roleType":1,
     * "token":"412a91e7c0a51acba4254d6d4da25700"}
     */

    private boolean success;
    private String errorCode;
    private String errorMessage;
    private String errorTips;
    private ModelBean model;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorTips() {
        return errorTips;
    }

    public void setErrorTips(String errorTips) {
        this.errorTips = errorTips;
    }

    public ModelBean getModel() {
        return model;
    }

    public void setModel(ModelBean model) {
        this.model = model;
    }

    public static class ModelBean {
        /**
         * name : 999
         * userId : 90665069410652160
         * projectId : 90665069377097730
         * phone : 17671756010
         * code : 3516
         * carNo :
         * carLicence : null
         * roleType : 1
         * token : 412a91e7c0a51acba4254d6d4da25700
         */

        private String name;
        private long userId;
        private long projectId;
        private String phone;
        private String code;
        private String carNo;
        private Object carLicence;
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

        public Object getCarLicence() {
            return carLicence;
        }

        public void setCarLicence(Object carLicence) {
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
