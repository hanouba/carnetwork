package com.carnetwork.hansen.mvp.model.http.api;


import com.carnetwork.hansen.mvp.model.http.response.MyHttpResponse;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface MyApis {


    String HOST = "http://121.36.3.165:6006/sys/";
    //    登录
    @POST("login")
    Flowable<String> login(@QueryMap Map<String, String> map);




}
