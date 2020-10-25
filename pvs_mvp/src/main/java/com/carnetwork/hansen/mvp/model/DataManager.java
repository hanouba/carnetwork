package com.carnetwork.hansen.mvp.model;




import com.carnetwork.hansen.mvp.model.bean.AllCar;
import com.carnetwork.hansen.mvp.model.bean.LoginBean;
import com.carnetwork.hansen.mvp.model.bean.LoginEntity;
import com.carnetwork.hansen.mvp.model.bean.UploadMapEntity;
import com.carnetwork.hansen.mvp.model.db.DBHelper;
import com.carnetwork.hansen.mvp.model.db.LoginInfo;
import com.carnetwork.hansen.mvp.model.http.HttpHelper;
import com.carnetwork.hansen.mvp.model.http.response.MyHttpResponse;
import com.carnetwork.hansen.mvp.model.prefs.PreferencesHelper;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;


/**
 *
 */

public class DataManager implements HttpHelper, PreferencesHelper, DBHelper {

    HttpHelper mHttpHelper;

    PreferencesHelper mPreferencesHelper;
    DBHelper mDBHelper;

    public DataManager(HttpHelper httpHelper, PreferencesHelper preferencesHelper, DBHelper dbHelper) {
        mHttpHelper = httpHelper;
        mDBHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
    }


    @Override
    public void insertLoginUserInfo(LoginInfo mLoginUserInfo) {

    }

    @Override
    public List<LoginInfo> loadLoginUserInfo() {
        return null;
    }

    @Override
    public void setLoginUerInfo(String name, String psd, String ip, String port) {

    }

    @Override
    public LoginInfo getUserInfo() {
        return null;
    }

    @Override
    public Flowable<LoginBean> getLogin(LoginEntity loginEntity) {
        return mHttpHelper.getLogin(loginEntity);
    }

    @Override
    public Flowable<MyHttpResponse> upLoadMap(UploadMapEntity uploadMapEntity) {
        return mHttpHelper.upLoadMap(uploadMapEntity);
    }

    @Override
    public Flowable<AllCar> getAllCar(String carNo) {
        return mHttpHelper.getAllCar(carNo);
    }
}
