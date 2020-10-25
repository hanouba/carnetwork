package com.carnetwork.hansen.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.CompoundButton;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.carnetwork.hansen.R;
import com.carnetwork.hansen.app.Constants;
import com.carnetwork.hansen.app.MyApplication;
import com.carnetwork.hansen.base.BaseActivity;
import com.carnetwork.hansen.mvp.contract.main.LoginContract1;
import com.carnetwork.hansen.mvp.model.bean.LoginEntity;
import com.carnetwork.hansen.mvp.model.db.LoginInfo;
import com.carnetwork.hansen.mvp.presenter.main.LoginPresenter1;
import com.carnetwork.hansen.util.SystemUtil;
import com.carnetwork.hansen.widget.LineEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresenter1>
        implements LoginContract1.View, CompoundButton.OnCheckedChangeListener {


    @BindView(R.id.et_car_no)
    LineEditText etCarNo;
    @BindView(R.id.et_carlicence)
    LineEditText etCarlicence;
    @BindView(R.id.et_phone)
    LineEditText etPhone;
    @BindView(R.id.et_name)
    LineEditText etName;
    @BindView(R.id.bt_login)
    Button btLogin;
    private String YHXY = "<<隐私授权协议>>";

    @Override
    protected int getLayout() {
        return R.layout.activity_login1;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }


    @Override
    protected void initEventAndData() {


    }


    void initListener() {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }


    @Override
    public void showErrorDialog(String msg) {
        ToastUtils.showShort(msg);
    }


    @Override
    public void gotoMainActivity() {


        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void loginFail(String msg) {

        dismissProcessDialog();
        ToastUtils.showShort(msg);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            try {
                finish();
                MyApplication.getInstance().exitApp();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    @OnClick(R.id.bt_login)
    public void onViewClicked() {

        String carNo = etCarNo.getText().toString().trim();
        String carLicence = etCarlicence.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String name = etName.getText().toString().trim();
        if (TextUtils.isEmpty(carNo)) {
            ToastUtils.showLong("车辆编号不能为空");
            return;
        }
        if (TextUtils.isEmpty(carLicence)) {
            ToastUtils.showLong("车牌号不能为空");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showLong("手机号不能为空");

            return;
        }else {
            if (!SystemUtil.isChinaPhoneLegal(phone)){
                ToastUtils.showLong("手机号格式不对");
                return;
            }
        }
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showLong("姓名不能为空");
            return;
        }

        SPUtils.getInstance().put(Constants.CAR_NO, carNo);
        showProcessDialog("登录中...");
        LoginEntity postingString = new LoginEntity(carNo,carLicence,phone,name);// json传递

        mPresenter.login(postingString);

    }
}
