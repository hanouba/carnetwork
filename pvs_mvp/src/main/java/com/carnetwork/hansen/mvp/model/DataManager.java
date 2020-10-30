package com.carnetwork.hansen.mvp.model;




import com.carnetwork.hansen.mvp.model.bean.AllCar;
import com.carnetwork.hansen.mvp.model.bean.AllDrvier;
import com.carnetwork.hansen.mvp.model.bean.LoginBean;
import com.carnetwork.hansen.mvp.model.bean.LoginEntity;
import com.carnetwork.hansen.mvp.model.bean.SateBean;
import com.carnetwork.hansen.mvp.model.bean.SateSaveEntity;
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
        mDBHelper.insertLoginUserInfo(mLoginUserInfo);
    }

    @Override
    public List<LoginInfo> loadLoginUserInfo() {
        return mDBHelper.loadLoginUserInfo();
    }

    @Override
    public LoginInfo loadByCarNo(String carNo) {
        return mDBHelper.loadByCarNo(carNo);
    }



    @Override
    public Flowable<LoginBean> getLogin(LoginEntity loginEntity) {
        return mHttpHelper.getLogin(loginEntity);
    }

    @Override
    public Flowable<MyHttpResponse<List<SateBean>>> getSateList(String carNo) {
        return mHttpHelper.getSateList(carNo);
    }

    @Override
    public Flowable<MyHttpResponse> upLoadMap(UploadMapEntity uploadMapEntity) {
        return mHttpHelper.upLoadMap(uploadMapEntity);
    }

    @Override
    public Flowable<MyHttpResponse> sateSave(SateSaveEntity sateSaveEntity) {
        return mHttpHelper.sateSave(sateSaveEntity);
    }

    @Override
    public Flowable<MyHttpResponse<List<AllCar>>> getAllCar(String carNo) {
        return mHttpHelper.getAllCar(carNo);
    }

    @Override
    public Flowable<MyHttpResponse> logout(String carNo) {
        return mHttpHelper.logout(carNo);
    }

    @Override
    public Flowable<MyHttpResponse<List<AllDrvier>>> getAllDriver(String carNo) {
        return mHttpHelper.getAllDriver(carNo);
    }

    @Override
    public String getToken() {
        return mPreferencesHelper.getToken();
    }

    @Override
    public void setToken(String token) {
        mPreferencesHelper.setToken(token);
    }

    @Override
    public void setWorkState(boolean state) {
        mPreferencesHelper.setWorkState(state);
    }

    @Override
    public boolean getWorkState() {
        return mPreferencesHelper.getWorkState();
    }
}
