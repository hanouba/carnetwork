package com.carnetwork.hansen.mvp.model.db;


import java.util.List;

/**
 *
 */

public interface DBHelper {
    void insertLoginUserInfo(LoginInfo mLoginUserInfo);

    List<LoginInfo> loadLoginUserInfo();
    LoginInfo loadByCarNo(String carNo);



}
