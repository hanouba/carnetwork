package com.carnetwork.hansen.mvp.model.http.api;


import com.carnetwork.hansen.mvp.model.bean.AllCar;
import com.carnetwork.hansen.mvp.model.bean.AllDrvier;
import com.carnetwork.hansen.mvp.model.bean.LoginBean;
import com.carnetwork.hansen.mvp.model.bean.LoginEntity;
import com.carnetwork.hansen.mvp.model.bean.SateBean;
import com.carnetwork.hansen.mvp.model.bean.SateSaveEntity;
import com.carnetwork.hansen.mvp.model.bean.UploadMapEntity;
import com.carnetwork.hansen.mvp.model.http.response.MyHttpResponse;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MyApis {


    String HOST = "http://121.36.3.165:6006/";

    //    登录
    @POST("sys/login")
    Flowable<LoginBean> login(@Body LoginEntity loginEntity);

    @POST("sys/logout")
    Flowable<MyHttpResponse> logout(@Query("carNo") String carNo);
    ///sys/logoff 下班不显示其他车辆
    @POST("sys/logoff")
    Observable<MyHttpResponse> logoff(@Query("carNo") String carNo,@Header("X-APP-TOKEN") String token);
    @POST("sys/logon")
    Observable<MyHttpResponse> logon(@Query("carNo") String carNo,@Header("X-APP-TOKEN") String token);

    //    所有的车辆
    @POST("map/allCar")
    Flowable<MyHttpResponse<List<AllCar>>> getAllCar(@Query("carNo") String carNo);


    @POST("map/allCar")
    Observable<MyHttpResponse<List<AllCar>>> getAllCarV(@Query("carNo") String carNo,@Header("X-APP-TOKEN") String token);

    //提交经纬度 车辆
    @POST("map/upload/v2")
    Flowable<MyHttpResponse> mapUpload(@Body UploadMapEntity uploadMapEntity);

    //所有的人员列表
    @POST("user/list")
    Flowable<MyHttpResponse<List<AllDrvier>>> getAllDriver(@Query("carNo") String carNo);

    //提交起点终点
    @POST("sate/save")
    Flowable<MyHttpResponse> sateSave(@Body SateSaveEntity sateSaveEntity);

    //获取起点终点
    @POST("sate/list")
    Flowable<MyHttpResponse<List<SateBean>>> getSateList(@Query("carNo") String carNo);
    //删除某个站点
    @POST("sate/delete")
    Flowable<MyHttpResponse> deleteSate(@Query("id") String id);
}
