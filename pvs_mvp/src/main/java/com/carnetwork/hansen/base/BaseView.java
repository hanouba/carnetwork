package com.carnetwork.hansen.base;

/**
 * author：Sun on 2017/8/25/0025.
 * email：1564063766@qq.com
 * remark:View基类
 */
public interface BaseView {

    void useNightMode(boolean isNight);

    //=======  State  =======
    void stateError();

    void stateEmpty();

    void stateLoading();

    void stateMain();
}
