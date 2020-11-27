package com.carnetwork.hansen.ui.main.activity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import android.util.Log;
import android.view.KeyEvent;

import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.carnetwork.hansen.R;
import com.carnetwork.hansen.app.Constants;
import com.carnetwork.hansen.app.MyApplication;
import com.carnetwork.hansen.base.BaseActivity;
import com.carnetwork.hansen.mvp.contract.main.LoginContract1;
import com.carnetwork.hansen.mvp.model.bean.LoginEntity;
import com.carnetwork.hansen.mvp.model.bean.ProjectEntity;
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

import static com.carnetwork.hansen.app.Constants.KEY_START_TIME;

public class LoginActivity extends BaseActivity<LoginPresenter1>
        implements LoginContract1.View, CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    /**
     * 手机号
     */
    @BindView(R.id.et_phone)
    MaterialEditText etPhone;
    /**
     * 用户名
     *
     */
    @BindView(R.id.et_name)
    MaterialEditText etUserName;
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
    /**
     * 登录
     */
    @BindView(R.id.bt_login)
    Button btLogin;
    private String YHXY = "<<隐私授权协议>>";

    /**
     * 计时器 倒计时60秒
     */
    private int currentSecond;
    //总时间60秒
    private static final int COUNTDOWN = 60;
    private String phone;

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
            String lastPhone = loginInfo.getPhone();
            String lastProject = loginInfo.getProjectName();
            etPhone.setText(lastPhone);
            etProjectName.setText(lastProject);
        }

        tvGetVer.setOnClickListener(this);

        tvGetVer.setEnabled(true);
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

        phone = etPhone.getText().toString().trim();
        String userName = etUserName.getText().toString().trim();
        //车队名称
        String projectName = etProjectName.getText().toString().trim();
        //验证码
        String verification = etVerification.getText().toString().trim();

        AssetManager assetManager = getResources().getAssets();
        try {
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd("");
            FileDescriptor fileDescriptor = assetFileDescriptor.getFileDescriptor();
        } catch (IOException e) {
            e.printStackTrace();
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
        if (TextUtils.isEmpty(verification)) {
            ToastUtils.showLong("验证码不能为空");
            return;
        }


        SPUtils.getInstance().put(Constants.CAR_PHONE, phone);
        showProcessDialog("登录中...");
        LoginEntity postingString = new LoginEntity(phone,verification,projectName);// json传递
        ProjectEntity projectEntity = new ProjectEntity(verification,phone,projectName,userName);
        /**
         * 存储登录信息
         * 手机号 车队名称
         */
        LoginInfo loginInfo = new LoginInfo(userName, phone,projectName);
        mPresenter.inserLoginInfo(loginInfo);
        mPresenter.login(postingString,projectEntity);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_get_ver:
//            获取验证码
                long startTime = SPUtils.getInstance().getLong(KEY_START_TIME,0);
                phone = etPhone.getText().toString().trim();
                long currentTime = System.currentTimeMillis();
                if (phone == null) {
                    return;
                }
                if (currentTime - startTime < COUNTDOWN * 1000) {
                    ToastUtils.showShort(R.string.smssdk_busy_hint);
                   return;
                }

                if (!SystemUtil.isNetworkConnected()) {
                    ToastUtils.showShort(R.string.smssdk_network_error);
                    return;
                }
                //检查验证码
                mPresenter.getMessageCode(phone);


            break;
            default:
        }
    }


 Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (tvGetVer != null) {
                if (currentSecond > 0) {
                    //更新 验证码计时显示
                    tvGetVer.setText("获取验证码" + " (" + currentSecond + "s)");
                    tvGetVer.setEnabled(false);
                    //每次减少1秒
                    currentSecond--;
                    //延迟一秒执行
                    handler.sendEmptyMessageDelayed(0, 1000);
                } else {
                    //恢复默认
                    tvGetVer.setText("获取验证码");
                    tvGetVer.setEnabled(true);
                }
            }
        }
    };
}
