package com.carnetwork.hansen.ui.main.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.carnetwork.hansen.R;
import com.carnetwork.hansen.app.Constants;
import com.carnetwork.hansen.app.MyApplication;
import com.carnetwork.hansen.base.BaseActivity;
import com.carnetwork.hansen.component.RxBus;
import com.carnetwork.hansen.mvp.contract.main.MainContract;
import com.carnetwork.hansen.mvp.model.bean.UploadMapEntity;
import com.carnetwork.hansen.mvp.model.event.CommonEvent;
import com.carnetwork.hansen.mvp.model.event.EventCode;
import com.carnetwork.hansen.mvp.presenter.main.MainPresenter;
import com.carnetwork.hansen.ui.main.fragment.MapFragment;
import com.carnetwork.hansen.util.PccGo2MapUtil;
import com.carnetwork.hansen.util.StatusBarUtil;
import com.carnetwork.hansen.widget.SwitchButton;


/**
 * Created by codeest on 16/8/9.
 */

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View,  View.OnClickListener {

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private  LocationClientOption option = new LocationClientOption();



    private TextView tvCarNo, tvCarLicence, tvUserName, tvUserPhone;
    private String userName, userPhone, carNo, carLicence;
    private Button carInfos;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    private void initBaiDuLocation() {


        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
        //可选，设置返回经纬度坐标类型，默认GCJ02
        //GCJ02：国测局坐标；
        //BD09ll：百度经纬度坐标；
        //BD09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标

        option.setScanSpan(30000);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
        //可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5*60*1000);
        //可选，V7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位

        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        option.setNeedNewVersionRgc(true);
        //可选，设置是否需要最新版本的地址信息。默认需要，即参数为true

        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
    }

    @Override
    protected void initEventAndData() {

        replaceFragment(new MapFragment(), R.id.fl_main_content);
        //  获取控件
        tvCarNo = findViewById(R.id.tv_car_no);
        tvCarLicence = findViewById(R.id.tv_car_licence);
        tvUserName = findViewById(R.id.tv_user_name);
        tvUserPhone = findViewById(R.id.tv_user_phone);
        Button logOut = findViewById(R.id.bt_logout);
        SwitchButton switchButton = findViewById(R.id.switch_button);
         carInfos = findViewById(R.id.bt_opencarinfo);


        location();

        //获取用户信息
        userName = SPUtils.getInstance().getString(Constants.CAR_NAME);
        userPhone = SPUtils.getInstance().getString(Constants.CAR_PHONE);
        carNo = SPUtils.getInstance().getString(Constants.CAR_NO);
        carLicence = SPUtils.getInstance().getString(Constants.CAR_LICENCE);

        //设置用户信息
        tvCarNo.setText(carNo);
        tvCarLicence.setText(carLicence);
        tvUserName.setText(userName);
        tvUserPhone.setText(userPhone);

        //退出
        logOut.setOnClickListener(this::onClick);
        carInfos.setOnClickListener(this);
        //工作状态设置

        boolean workState =   SPUtils.getInstance().getBoolean(Constants.IS_ON_WORK);
        switchButton.setChecked(workState);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPUtils.getInstance().put(Constants.IS_ON_WORK, isChecked);
                RxBus.getDefault().post(new CommonEvent(EventCode.WORK_STATE,isChecked));



            }
        });



    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    /*开启定位*/
    private void location() {

        LocationManager   lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean providerEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (providerEnabled) {
            //声明mLocationOption对象
            initBaiDuLocation();
            mLocationClient.start();

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
    public void logOutSuccess() {
        try {
            Thread.sleep(500);
            MyApplication.getInstance().exitApp();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }




    @Override
    protected void onResume() {
        super.onResume();
        if (null != mLocationClient && !mLocationClient.isStarted()) {
            mLocationClient.start();
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
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
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
            mLocationClient.disableAssistantLocation();
        }


    }


    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, null);
    }

    private String selectDate = "";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_logout:

                SPUtils.getInstance().put(Constants.IS_ON_WORK, false);
                mPresenter.logout(carNo);
                break;
            case R.id.bt_opencarinfo:
                startActivity(new Intent(this,CarInfosActivity.class));

                break;
            default:
        }
    }

    public class MyLocationListener extends BDAbstractLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            double latitude = bdLocation.getLatitude();    //获取纬度信息
            double longitude = bdLocation.getLongitude();    //获取经度信息
            float radius = bdLocation.getRadius();    //获取定位精度，默认值为0.0f

            String coorType = bdLocation.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

            int errorCode = bdLocation.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            LogUtils.d("定位失败"+errorCode+"longitude"+longitude);

            boolean isWork = SPUtils.getInstance().getBoolean(Constants.IS_ON_WORK);
            //                定时上传车辆信息
            UploadMapEntity uploadMapEntity = new UploadMapEntity(carNo, Double.toString(latitude), Double.toString(longitude), userName, userPhone);
            if (isWork) {
                mPresenter.mapUpLoad(uploadMapEntity);
            }


        }
    }
}
