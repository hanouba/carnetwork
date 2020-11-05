package com.carnetwork.hansen.mvp.presenter.main;

import android.util.Log;

import com.baidu.mapapi.search.core.PoiInfo;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.carnetwork.hansen.app.Constants;
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
import com.carnetwork.hansen.mvp.model.http.api.MyApis;
import com.carnetwork.hansen.mvp.model.http.response.MyHttpResponse;
import com.carnetwork.hansen.ui.main.fragment.MapFragment;
import com.carnetwork.hansen.util.RxUtil;
import com.carnetwork.hansen.widget.CommonSubscriber;


import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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
    private static final String TAG = "MapPresenter_tag";
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyApis.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        MyApis request = retrofit.create(MyApis.class);

         Observable<MyHttpResponse<List<AllCar>>> observable = request.getAllCarV(carNo,token);

        observable.repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Object o) throws Exception {

                        return Observable.just(1).delay(30000, TimeUnit.MILLISECONDS);
                    }
                });
            }
        }).subscribeOn(Schedulers.io())   // 切换到IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //切换到主线程 处理请求结果
                .subscribe(new Observer<MyHttpResponse<List<AllCar>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(TAG, "button2_onSubscribe");
                    }
                    @Override
                    public void onNext(MyHttpResponse<List<AllCar>> AllCar) {
                        LogUtils.d(TAG, "button2_onNext"+AllCar.isSuccess());
                        if (AllCar.isSuccess()) {
                            mView.showAllCar(AllCar.getModel());
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(TAG, "button2_onError");
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.d(TAG, "button2_onComplete  ");
                    }
                });

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

    @Override
    public void deleateSate(String carNo,String id) {
        addSubscribe(mDataManager.deleteSate(id)
                .compose(RxUtil.<MyHttpResponse>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<MyHttpResponse>(mView) {
                    @Override
                    public void onNext(MyHttpResponse sateBeans) {
                        if (sateBeans.isSuccess()) {
                        getSateList(carNo);
                        }else {
                            ToastUtils.showShort("删除失败");
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
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
