package com.carnetwork.hansen.mvp.model.http.manege;

import android.text.TextUtils;

/**
 * Created by yu on 2017/7/24.
 */
public class InvalidUrlException extends RuntimeException {

    public InvalidUrlException(String url) {
        super("You'icon_vnn configured an invalid url : " + (TextUtils.isEmpty(url) ? "EMPTY_OR_NULL_URL" : url));
    }
}
