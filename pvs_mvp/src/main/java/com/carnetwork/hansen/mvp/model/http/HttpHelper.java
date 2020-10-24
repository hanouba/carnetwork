package com.carnetwork.hansen.mvp.model.http;


import java.util.Map;

import io.reactivex.Flowable;

/**

 */

public interface HttpHelper {

    Flowable<String> getLogin(Map res);


}
