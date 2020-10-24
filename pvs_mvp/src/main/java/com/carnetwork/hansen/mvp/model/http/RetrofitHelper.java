package com.carnetwork.hansen.mvp.model.http;


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
    public Flowable<String> getLogin( Map res) {
        return mMyApiService.login(res);
    }
}
