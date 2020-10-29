package com.carnetwork.hansen.mvp.presenter.main;

import android.util.Log;

import com.carnetwork.hansen.base.RxPresenter;
import com.carnetwork.hansen.component.RxBus;
import com.carnetwork.hansen.mvp.contract.main.CarInfoContract;
import com.carnetwork.hansen.mvp.contract.main.MapContract;
import com.carnetwork.hansen.mvp.model.DataManager;
import com.carnetwork.hansen.mvp.model.bean.AllCar;
import com.carnetwork.hansen.mvp.model.bean.AllDrvier;
import com.carnetwork.hansen.mvp.model.event.CommonEvent;
import com.carnetwork.hansen.mvp.model.event.EventCode;
import com.carnetwork.hansen.mvp.model.http.response.MyHttpResponse;
import com.carnetwork.hansen.util.RxUtil;
import com.carnetwork.hansen.widget.CommonSubscriber;

import java.util.List;

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
public class DriverInfoPresenter extends RxPresenter<CarInfoContract.View> implements CarInfoContract.Presenter {

    private DataManager mDataManager;
    @Inject
    public DriverInfoPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }
    @Override
    public void attachView(CarInfoContract.View view) {
        super.attachView(view);
        registerEvent();
    }


    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(CommonEvent.class)
                .compose(RxUtil.<CommonEvent>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<CommonEvent>(mView, null) {

                    @Override
                    public void onNext(CommonEvent commonevent) {
                        switch (commonevent.getCode()) {
                            case EventCode.WORK_STATE:
                                break;
                            default:

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        registerEvent();
                    }
                })
        );
    }

    @Override
    public void getAllDriver(String token, String carNo) {



        addSubscribe(mDataManager.getAllDriver(carNo)
                .compose(RxUtil.<MyHttpResponse<List<AllDrvier>>>rxSchedulerHelper())
                .compose(RxUtil.<List<AllDrvier>>handleMyResult())
                .subscribeWith(new CommonSubscriber<List<AllDrvier>>(mView) {
                    @Override
                    public void onNext(List<AllDrvier> allCar) {
                        Log.i("", "onNext: 获取到AllDrvier"+allCar.toString());
                        mView.showAllDriver(allCar);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.i("", "onNext: 获取到AllDrvieronError"+e.toString());
                    }
                })
        );
    }


}
