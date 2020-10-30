package com.carnetwork.hansen.mvp.presenter.main;

import android.util.Log;

import com.baidu.mapapi.search.core.PoiInfo;
import com.blankj.utilcode.util.LogUtils;
import com.carnetwork.hansen.base.RxPresenter;
import com.carnetwork.hansen.component.RxBus;
import com.carnetwork.hansen.mvp.contract.main.MainContract;
import com.carnetwork.hansen.mvp.contract.main.MapContract;
import com.carnetwork.hansen.mvp.model.DataManager;
import com.carnetwork.hansen.mvp.model.bean.AllCar;
import com.carnetwork.hansen.mvp.model.bean.AllDrvier;
import com.carnetwork.hansen.mvp.model.bean.LoginBean;
import com.carnetwork.hansen.mvp.model.bean.SateBean;
import com.carnetwork.hansen.mvp.model.bean.SateSaveEntity;
import com.carnetwork.hansen.mvp.model.bean.UploadMapEntity;
import com.carnetwork.hansen.mvp.model.event.CommonEvent;
import com.carnetwork.hansen.mvp.model.event.EventCode;
import com.carnetwork.hansen.mvp.model.http.response.MyHttpResponse;
import com.carnetwork.hansen.ui.main.fragment.MapFragment;
import com.carnetwork.hansen.util.RxUtil;
import com.carnetwork.hansen.widget.CommonSubscriber;

import org.reactivestreams.Publisher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

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
    @Override
    public void attachView(MapContract.View view) {
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
                            mView.changeWorkState();
                                break;
                            case EventCode.POIINFO:
                                PoiInfo poiInfo = commonevent.getPoiInfo();
                                LogUtils.d("sateType" + poiInfo.name+"statetype--"+MapFragment. sateType);
                                String latitude = String.valueOf(poiInfo.location.latitude);
                                String longitude = String.valueOf(poiInfo.location.longitude);
                                SateSaveEntity sateSaveEntity = new SateSaveEntity(latitude,longitude,poiInfo.name,  MapFragment. sateType);
                                sateSave(sateSaveEntity);
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
    public void getAllCar(String token, String carNo) {



        addSubscribe(mDataManager.getAllCar(carNo)

                .compose(RxUtil.<MyHttpResponse<List<AllCar>>>rxSchedulerHelper())
                .compose(RxUtil.<List<AllCar>>handleMyResult())
                .repeatWhen(new Function<Flowable<Object>, Publisher<?>>() {
                    @Override
                    public Publisher<?> apply(Flowable<Object> objectFlowable) throws Exception {
                        Log.i("", "onNext: 延迟一秒轮询");
                        return Flowable.timer(1000, TimeUnit.MILLISECONDS);
                    }
                })
                .subscribeWith(new CommonSubscriber<List<AllCar>>(mView) {
                    @Override
                    public void onNext(List<AllCar> allCar) {
                        Log.i("", "onNext: 获取到allCar"+allCar.toString());
                        mView.showAllCar(allCar);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.i("", "onNext: 获取到onError"+e.toString());
                    }
                })
        );
    }


    @Override
    public void mapUpLoad(UploadMapEntity uploadMapEntity) {
        addSubscribe(mDataManager.upLoadMap(uploadMapEntity)

                .compose(RxUtil.<MyHttpResponse>rxSchedulerHelper())

                .subscribeWith(new CommonSubscriber<MyHttpResponse>(mView) {
                    @Override
                    public void onNext(MyHttpResponse myHttpResponse) {
                        Log.i("", "mapUpLoad: 提交经纬度"+myHttpResponse.isSuccess());

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.i("", "onNext: 提交经纬度失败"+e.toString());
                    }
                })
        );
    }

    @Override
    public void getSateList(String carNo) {

        addSubscribe(mDataManager.getSateList(carNo)
                .compose(RxUtil.<MyHttpResponse<List<SateBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<SateBean>>handleMyResult())
                .subscribeWith(new CommonSubscriber<List<SateBean>>(mView) {
                    @Override
                    public void onNext(List<SateBean> sateBeans) {
                        Log.i("", "onNext: 获取到起点终点"+sateBeans.toString());
                        mView.showAllSate(sateBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.i("", "onNext: 获取到获取到起点终点onError"+e.toString());
                    }
                })
        );
    }

    public void sateSave(SateSaveEntity sateSaveEntity) {
        addSubscribe(mDataManager.sateSave(sateSaveEntity)

                .compose(RxUtil.<MyHttpResponse>rxSchedulerHelper())

                .subscribeWith(new CommonSubscriber<MyHttpResponse>(mView) {
                    @Override
                    public void onNext(MyHttpResponse myHttpResponse) {
                        LogUtils.i("", "mapUpLoad: 起点终点"+myHttpResponse.isSuccess());

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        LogUtils.i("", "onNext: 起点终点失败"+e.toString());
                    }
                })
        );
    }

}
