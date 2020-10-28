package com.carnetwork.hansen.ui.main.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
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

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, AMapLocationListener, View.OnClickListener {


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

    private TextView tvCarNo, tvCarLicence, tvUserName, tvUserPhone;
    private String userName, userPhone, carNo, carLicence;
    private Button carInfos;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
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
        mLocationOption.setInterval(3000);

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
    public void logOutSuccess() {
        try {
            Thread.sleep(500);
            MyApplication.getInstance().exitApp();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
                boolean isWork = SPUtils.getInstance().getBoolean(Constants.IS_ON_WORK);
//                定时上传车辆信息
                UploadMapEntity uploadMapEntity = new UploadMapEntity(carNo, Double.toString(latitude), Double.toString(longitude), userName, userPhone);
                if (isWork) {
                    mPresenter.mapUpLoad(uploadMapEntity);
                }

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
            mLocationClient.disableBackgroundLocation(true);
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
}
