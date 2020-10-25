package com.carnetwork.hansen.mvp.model.http;


import com.carnetwork.hansen.mvp.model.bean.AllCar;
import com.carnetwork.hansen.mvp.model.bean.LoginBean;
import com.carnetwork.hansen.mvp.model.bean.LoginEntity;
import com.carnetwork.hansen.mvp.model.http.api.MyApis;

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
    public Flowable<AllCar> getAllCar(String carNo) {
        return mMyApiService.getAllCar(carNo);
    }
}
