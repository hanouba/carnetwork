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
        String token = SPUtils.getInstance().getString(Constants.TOKEN);


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
        if (workState) {
            mPresenter.logOn(carNo,token);
        }else {

        }
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPUtils.getInstance().put(Constants.IS_ON_WORK, isChecked);
                RxBus.getDefault().post(new CommonEvent(EventCode.WORK_STATE,isChecked));
                if (isChecked) {
                    mPresenter.logOn(carNo,token);
                }else {
                    mPresenter.logOff(carNo,token);
                }
            }
        });



    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
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

                mPresenter.logout(carNo);
                break;
            case R.id.bt_opencarinfo:
                startActivity(new Intent(this,CarInfosActivity.class));

                break;
            default:
        }
    }


}
