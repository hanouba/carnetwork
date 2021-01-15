package com.carnetwork.hansen.mvp.model.http;


import com.carnetwork.hansen.mvp.model.bean.AllCar;
import com.carnetwork.hansen.mvp.model.bean.AllDrvier;
import com.carnetwork.hansen.mvp.model.bean.CarListBean;
import com.carnetwork.hansen.mvp.model.bean.LoginBean;
import com.carnetwork.hansen.mvp.model.bean.LoginEntity;
import com.carnetwork.hansen.mvp.model.bean.ProjectEntity;
import com.carnetwork.hansen.mvp.model.bean.SateBean;
import com.carnetwork.hansen.mvp.model.bean.SateSaveEntity;
import com.carnetwork.hansen.mvp.model.bean.UploadMapEntity;
import com.carnetwork.hansen.mvp.model.bean.UserRoleEntity;
import com.carnetwork.hansen.mvp.model.http.response.MyHttpResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.Header;

/**

 */

public interface HttpHelper {

    Flowable<LoginBean> getLogin(LoginEntity loginEntity);
    Flowable<LoginBean> getLoginV2(LoginEntity loginEntity);
    Flowable<MyHttpResponse<List<SateBean>>> getSateList(String carNo);
    Flowable<MyHttpResponse> upLoadMap(UploadMapEntity uploadMapEntity);
    Flowable<MyHttpResponse> sateSave(SateSaveEntity sateSaveEntity);
    Flowable<MyHttpResponse<List<AllCar>>> getAllCar(String carNo);
    Flowable<MyHttpResponse> logout(String carNo);
    Flowable<MyHttpResponse> deleteSate(String id);

    Flowable<MyHttpResponse> createProject(ProjectEntity projectEntity);
    Flowable<MyHttpResponse<List<AllDrvier>>> getAllDriver(String carNo);

    Flowable<MyHttpResponse<List<CarListBean>>> getCarCanUse(@Header("X-APP-TOKEN") String token);


    Flowable<MyHttpResponse> userCreate(ProjectEntity projectEntity);
    Flowable<MyHttpResponse> userDisable(UserRoleEntity userRoleEntity);
    Flowable<MyHttpResponse> userUpdateRole(UserRoleEntity userRoleEntity);
}
