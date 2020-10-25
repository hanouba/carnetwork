package com.carnetwork.hansen.mvp.model.http.api;


import android.support.annotation.RawRes;

import com.carnetwork.hansen.mvp.model.bean.AllCar;
import com.carnetwork.hansen.mvp.model.bean.LoginBean;
import com.carnetwork.hansen.mvp.model.bean.LoginEntity;
import com.carnetwork.hansen.mvp.model.http.response.MyHttpResponse;

import org.greenrobot.greendao.annotation.Entity;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface MyApis {


    String HOST = "http://121.36.3.165:6006/";
    //    登录
    @POST("sys/login")

    Flowable<LoginBean> login(@Body LoginEntity loginEntity);

    //    回去车辆
    @POST("map/allCar")
    Flowable<AllCar> getAllCar(@Query("carNo") String carNo);



    @POST("map/upload")
    Flowable<MyHttpResponse> mapUpload(@Body UploadMapEntity uploadMapEntity);
}
