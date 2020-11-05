package com.carnetwork.hansen.ui.main.activity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;

import android.text.TextUtils;

import android.view.KeyEvent;

import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

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
import com.carnetwork.hansen.util.Music;
import com.carnetwork.hansen.util.MusicAdapter;
import com.carnetwork.hansen.util.SystemUtil;
import com.carnetwork.hansen.widget.LineEditText;
import com.hansen.edittextlib.materialedittext.MaterialEditText;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresenter1>
        implements LoginContract1.View, CompoundButton.OnCheckedChangeListener, View.OnClickListener {


    @BindView(R.id.et_car_no)
    MaterialEditText etCarNo;
    @BindView(R.id.et_carlicence)
    MaterialEditText etCarlicence;
    @BindView(R.id.et_phone)
    MaterialEditText etPhone;
    @BindView(R.id.et_name)
    MaterialEditText etName;
    /**
     * 获取验证码
     */
    @BindView(R.id.tv_get_ver)
    TextView tvGetVer;
    /**
     * 车队名称
     */
    @BindView(R.id.et_project_name)
    MaterialEditText etProjectName;

    /**
     * 验证码
     */
    @BindView(R.id.et_verification)
    MaterialEditText etVerification;

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
        List<LoginInfo> loginInfos = mPresenter.getLoginInfo();
        if (loginInfos.size()>0) {
            LoginInfo loginInfo = loginInfos.get(0);
            String lastCarNo = loginInfo.getCarNo();
            String lastCarLicence = loginInfo.getCarLicence();
            String lastPhone = loginInfo.getPhone();
            String lastUserName = loginInfo.getUsername();
            String lastProject = loginInfo.getProjectName();



            etName.setText(lastUserName);
            etCarNo.setText(lastCarNo);
            etPhone.setText(lastPhone);
            etCarlicence.setText(lastCarLicence);
            etProjectName.setText(lastProject);
        }

        tvGetVer.setOnClickListener(this);
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

    private ArrayList<Music> arrayList;
    private MusicAdapter mMusicAdapter;
    private ListView mListView;

    @OnClick(R.id.bt_login)
    public void onViewClicked() {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
        String carNo = etCarNo.getText().toString().trim();
        String carLicence = etCarlicence.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String name = etName.getText().toString().trim();
        AssetManager assetManager = getResources().getAssets();
        try {
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd("");
            FileDescriptor fileDescriptor = assetFileDescriptor.getFileDescriptor();
        } catch (IOException e) {
            e.printStackTrace();
        }



        if (TextUtils.isEmpty(carNo)) {
            ToastUtils.showLong("车辆编号不能为ddddddd空");
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
        SPUtils.getInstance().put(Constants.CAR_NAME, name);
        SPUtils.getInstance().put(Constants.CAR_PHONE, phone);
        SPUtils.getInstance().put(Constants.CAR_LICENCE, carLicence);
        showProcessDialog("登录中...");
        LoginEntity postingString = new LoginEntity(carNo,carLicence,phone,name);// json传递

        LoginInfo loginInfo = new LoginInfo(name,carNo,carLicence,phone,"");
        mPresenter.inserLoginInfo(loginInfo);
        mPresenter.login(postingString);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_get_ver:
//            获取验证码

            break;
            default:
        }
    }
}
