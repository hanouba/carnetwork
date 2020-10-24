package com.carnetwork.hansen.mvp.model.prefs;

import com.carnetwork.hansen.mvp.model.db.LoginInfo;

/**
 * @author: Est <codeest.dev@gmail.com>
 * @date: 2017/4/21
 * @description:
 */

public interface PreferencesHelper {

    void setLoginUerInfo(String name,String psd,String ip,String port);

    LoginInfo getUserInfo();


}
