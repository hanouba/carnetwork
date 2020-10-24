package com.carnetwork.hansen.mvp.contract.main;

import com.carnetwork.hansen.base.BasePresenter;
import com.carnetwork.hansen.base.BaseView;

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
public interface MapContract {
    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<MapContract.View> {

    }
}
