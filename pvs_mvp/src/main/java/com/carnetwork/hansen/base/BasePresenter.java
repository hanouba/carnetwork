package com.carnetwork.hansen.base;

/**
 * author：Sun on 2017/8/25/0025.
 * email：1564063766@qq.com
 * remark:Presenter基类
 */
public interface BasePresenter<T extends com.carnetwork.hansen.base.BaseView>{

    void attachView(T view);

    void detachView();
}
