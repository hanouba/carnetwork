package com.carnetwork.hansen.mvp.model.http;


import com.carnetwork.hansen.mvp.model.bean.AllCar;
import com.carnetwork.hansen.mvp.model.bean.LoginBean;
import com.carnetwork.hansen.mvp.model.bean.LoginEntity;

import java.util.Map;

import io.reactivex.Flowable;

/**

 */

public interface HttpHelper {

    Flowable<LoginBean> getLogin(LoginEntity loginEntity);
    Flowable<AllCar> getAllCar(String carNo);

}
