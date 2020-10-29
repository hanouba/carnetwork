package com.carnetwork.hansen.mvp.model.http.api;


import com.carnetwork.hansen.mvp.model.bean.AllCar;
import com.carnetwork.hansen.mvp.model.bean.AllDrvier;
import com.carnetwork.hansen.mvp.model.bean.LoginBean;
import com.carnetwork.hansen.mvp.model.bean.LoginEntity;
import com.carnetwork.hansen.mvp.model.bean.UploadMapEntity;
import com.carnetwork.hansen.mvp.model.http.response.MyHttpResponse;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MyApis {


    String HOST = "http://121.36.3.165:6006/";
    //    登录
    @POST("sys/login")

    Flowable<LoginBean> login(@Body LoginEntity loginEntity);

    @POST("sys/logout")
    Flowable<MyHttpResponse> logout(@Query("carNo") String carNo);

    //    回去车辆
    @POST("map/allCar")
    Flowable<MyHttpResponse<List<AllCar>>> getAllCar(@Query("carNo") String carNo);

    @POST("map/upload")
    Flowable<MyHttpResponse> mapUpload(@Body UploadMapEntity uploadMapEntity);

    //回去人员列表
    @POST("user/list")
    Flowable<MyHttpResponse<List<AllDrvier>>> getAllDriver(@Query("carNo") String carNo);
}
