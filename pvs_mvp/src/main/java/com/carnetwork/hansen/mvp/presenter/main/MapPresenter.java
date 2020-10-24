package com.carnetwork.hansen.mvp.presenter.main;

import com.carnetwork.hansen.base.RxPresenter;
import com.carnetwork.hansen.mvp.contract.main.MainContract;
import com.carnetwork.hansen.mvp.contract.main.MapContract;
import com.carnetwork.hansen.mvp.model.DataManager;

import javax.inject.Inject;

/**
 * @author HanN on 2020/10/23 10:07
 * @email: 1356548475@qq.com
 * @project carnetwork
 * @description:
 * @updateuser:
 * @updatedata: 2020/10/23 10:07
 * @updateremark:
 * @version: 2.1.67
 */
public class MapPresenter  extends RxPresenter<MapContract.View> implements MapContract.Presenter {

    private DataManager mDataManager;
    @Inject
    public MapPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }
}
