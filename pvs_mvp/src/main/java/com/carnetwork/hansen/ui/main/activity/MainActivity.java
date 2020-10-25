package com.carnetwork.hansen.ui.main.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.blankj.utilcode.util.SPUtils;
import com.carnetwork.hansen.R;
import com.carnetwork.hansen.app.Constants;
import com.carnetwork.hansen.app.MyApplication;
import com.carnetwork.hansen.base.BaseActivity;
import com.carnetwork.hansen.mvp.contract.main.MainContract;
import com.carnetwork.hansen.mvp.model.bean.UploadMapEntity;
import com.carnetwork.hansen.mvp.model.db.LoginInfo;
import com.carnetwork.hansen.mvp.presenter.main.MainPresenter;
import com.carnetwork.hansen.ui.main.fragment.MapFragment;
import com.carnetwork.hansen.util.PccGo2MapUtil;
import com.carnetwork.hansen.util.StatusBarUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by codeest on 16/8/9.
 */

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, AMapLocationListener {


    /**
     * 高德定位
     */
    public AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption mLocationOption = null;
    private LocationManager lm;
    //定位结果
    private String locpvs = "";
;

    /**
     * 跳转至工作页 "未处理" 标识
     */
    private int keyCode = 0;


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }


    @Override
    protected void initEventAndData() {
        replaceFragment(new MapFragment(), R.id.fl_main_content);
        location();
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    /*开启定位*/
    private void location() {

        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean providerEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (providerEnabled) {
            //声明mLocationOption对象
            mLocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();

            //设置定位监听
            mLocationClient.setLocationListener(this);
            //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式  不使用GPS
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位间隔,单位毫秒,默认为100000ms
            mLocationOption.setInterval(10000);

            //设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.startLocation();

        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("");
            builder.setMessage("为了您能正常使用请打开GPS");
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent intent = new Intent(
                            Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
                }
            });
            builder.setPositiveButton("", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create().show();
        }

    }

    @Override
    public void resetLocation() {

        mLocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();

        //设置定位监听
        mLocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式  不使用GPS
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为100000ms
        mLocationOption.setInterval(10000);

        if (mLocationClient != null && mLocationOption != null) {
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.startLocation();
        } else {
            return;
        }

    }

    @Override
    public void relogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("登录信息失效,请重新登录")
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyApplication.getInstance().exitApp();
                    }
                });
        builder.show();
    }



    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                String address = aMapLocation.getAddress();
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                double latitude = aMapLocation.getLatitude();//获取纬度
                double longitude = aMapLocation.getLongitude();//获取经度
                float accuracy = aMapLocation.getAccuracy();//获取精度信息

                //                转换地位为百度定位
                double[] doubles = PccGo2MapUtil.gaoDeToBaidu(longitude, latitude);

                String name = SPUtils.getInstance().getString(Constants.CAR_NAME);
                String phone = SPUtils.getInstance().getString(Constants.CAR_PHONE);
                String carNo = SPUtils.getInstance().getString(Constants.CAR_NO);
                UploadMapEntity uploadMapEntity = new UploadMapEntity(carNo, Double.toString(latitude),Double.toString(longitude),name,phone);
                mPresenter.mapUpLoad(uploadMapEntity);

            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (null != mLocationClient && !mLocationClient.isStarted()) {
            mLocationClient.startLocation();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressedSupport() {
        showExitDialog();
    }

    private void showExitDialog() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定退出吗");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                MyApplication.getInstance().exitApp();



            }
        });
        builder.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.disableBackgroundLocation(true);
        }


    }



    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, null);
    }

    private String selectDate = "";

}
