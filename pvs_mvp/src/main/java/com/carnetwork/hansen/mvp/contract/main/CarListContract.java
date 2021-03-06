package com.carnetwork.hansen.mvp.contract.main;

import com.carnetwork.hansen.base.BasePresenter;
import com.carnetwork.hansen.base.BaseView;
import com.carnetwork.hansen.mvp.model.bean.AllDrvier;

import java.util.List;

/**
 * @author HanN on 2020/12/2 10:07
 * @email: 1356548475@qq.com
 * @project carnetwork
 * @description: 车辆信息
 * @updateuser:
 * @updatedata: 2020/12/2 10:07
 * @updateremark:
 * @version: 2.1.67
 */
public interface CarListContract {
    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<CarListContract.View> {

    }
}
