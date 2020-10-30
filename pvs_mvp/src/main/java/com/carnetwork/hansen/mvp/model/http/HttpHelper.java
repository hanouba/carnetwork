package com.carnetwork.hansen.mvp.model.http;


import com.carnetwork.hansen.mvp.model.bean.AllCar;
import com.carnetwork.hansen.mvp.model.bean.AllDrvier;
import com.carnetwork.hansen.mvp.model.bean.LoginBean;
import com.carnetwork.hansen.mvp.model.bean.LoginEntity;
import com.carnetwork.hansen.mvp.model.bean.SateBean;
import com.carnetwork.hansen.mvp.model.bean.SateSaveEntity;
import com.carnetwork.hansen.mvp.model.bean.UploadMapEntity;
import com.carnetwork.hansen.mvp.model.http.response.MyHttpResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;

/**

 */

public interface HttpHelper {

    Flowable<LoginBean> getLogin(LoginEntity loginEntity);
    Flowable<MyHttpResponse<List<SateBean>>> getSateList(String carNo);
    Flowable<MyHttpResponse> upLoadMap(UploadMapEntity uploadMapEntity);
    Flowable<MyHttpResponse> sateSave(SateSaveEntity sateSaveEntity);
    Flowable<MyHttpResponse<List<AllCar>>> getAllCar(String carNo);
    Flowable<MyHttpResponse> logout(String carNo);
    Flowable<MyHttpResponse<List<AllDrvier>>> getAllDriver(String carNo);

}
