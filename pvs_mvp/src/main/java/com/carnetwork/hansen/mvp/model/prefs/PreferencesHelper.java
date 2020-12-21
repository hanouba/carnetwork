package com.carnetwork.hansen.mvp.model.prefs;

import com.carnetwork.hansen.mvp.model.db.LoginInfo;

/**
 * @author: Est <codeest.dev@gmail.com>
 * @date: 2017/4/21
 * @description:
 */

public interface PreferencesHelper {

    String getToken();
    String getCarNo();
    void setToken(String token);
    void setCarNo(String carNo);

    void setWorkState(boolean state);
    boolean getWorkState();


}
