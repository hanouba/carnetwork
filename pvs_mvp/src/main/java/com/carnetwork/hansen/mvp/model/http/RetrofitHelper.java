package com.carnetwork.hansen.mvp.model.http;


import com.carnetwork.hansen.mvp.model.bean.AllCar;
import com.carnetwork.hansen.mvp.model.bean.AllDrvier;
import com.carnetwork.hansen.mvp.model.bean.LoginBean;
import com.carnetwork.hansen.mvp.model.bean.LoginEntity;
import com.carnetwork.hansen.mvp.model.bean.ProjectEntity;
import com.carnetwork.hansen.mvp.model.bean.SateBean;
import com.carnetwork.hansen.mvp.model.bean.SateSaveEntity;
import com.carnetwork.hansen.mvp.model.bean.UploadMapEntity;
import com.carnetwork.hansen.mvp.model.http.api.MyApis;
import com.carnetwork.hansen.mvp.model.http.response.MyHttpResponse;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Flowable;


/**
 * Created by codeest on 2016/8/3.
 */
public class RetrofitHelper implements HttpHelper {

    private MyApis mMyApiService;


    @Inject
    public RetrofitHelper(MyApis myApiService) {
        this.mMyApiService = myApiService;
    }


    @Override
    public Flowable<LoginBean> getLogin(LoginEntity map) {
        return mMyApiService.login(map);
    }

    @Override
    public Flowable<LoginBean> getLoginV2(LoginEntity loginEntity) {
        return mMyApiService.loginV2(loginEntity);
    }

    @Override
    public Flowable<MyHttpResponse<List<SateBean>>> getSateList(String carNo) {
        return mMyApiService.getSateList(carNo);
    }

    @Override
    public Flowable<MyHttpResponse> upLoadMap(UploadMapEntity uploadMapEntity) {
        return mMyApiService.mapUpload(uploadMapEntity);
    }

    @Override
    public Flowable<MyHttpResponse> sateSave(SateSaveEntity sateSaveEntity) {
        return mMyApiService.sateSave(sateSaveEntity);
    }

    @Override
    public Flowable<MyHttpResponse<List<AllCar>>> getAllCar(String carNo) {
        return mMyApiService.getAllCar(carNo);
    }

    @Override
    public Flowable<MyHttpResponse> logout(String carNo) {
        return mMyApiService.logout(carNo);
    }

    @Override
    public Flowable<MyHttpResponse> deleteSate(String id) {
        return mMyApiService.deleteSate(id);
    }

    @Override
    public Flowable<MyHttpResponse> createProject(ProjectEntity projectEntity) {
        return mMyApiService.createProject(projectEntity);
    }

    @Override
    public Flowable<MyHttpResponse<List<AllDrvier>>> getAllDriver(String carNo) {
        return mMyApiService.getAllDriver(carNo);
    }
}
