package com.carnetwork.hansen.ui.main.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.carnetwork.hansen.mvp.model.bean.LoginBean;
import com.carnetwork.hansen.mvp.model.bean.LoginEntity;
import com.carnetwork.hansen.mvp.model.bean.ProjectEntity;
import com.carnetwork.hansen.mvp.model.db.LoginInfo;
import com.carnetwork.hansen.mvp.presenter.main.LoginPresenter1;
import com.carnetwork.hansen.util.Music;
import com.carnetwork.hansen.util.MusicAdapter;
import com.carnetwork.hansen.util.SystemUtil;
import com.carnetwork.hansen.widget.LineEditText;
import com.hansen.edittextlib.materialedittext.MaterialEditText;

import org.w3c.dom.Text;

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
     * 名称
     */
    @BindView(R.id.tv_name)
    TextView tvName;
    /**
     * 姓名
     */
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
     * 创建项目
     */
    @BindView(R.id.tv_createProejct)
    TextView tvCreateProject;
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


    private LoginEntity postingString;

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
        if (loginInfos.size() > 0) {
            LoginInfo loginInfo = loginInfos.get(0);
            String lastPhone = loginInfo.getPhone();
            String lastProject = loginInfo.getProjectName();
            etPhone.setText(lastPhone);
            etProjectName.setText(lastProject);
        }

        tvGetVer.setOnClickListener(this);
        tvCreateProject.setOnClickListener(this);

        tvGetVer.setEnabled(true);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }


    @Override
    public void showErrorDialog(String msg) {
        ToastUtils.showShort(msg);
        dismissProcessDialog();
    }

    @Override
    public void gotoMainActivity(LoginBean loginBean) {
        String token = loginBean.getModel().getToken();
        long projectId = loginBean.getModel().getProjectId();
        SPUtils.getInstance().put(Constants.PROJECT_PROJECTID, projectId);
        SPUtils.getInstance().put(Constants.CAR_NAME, loginBean.getModel().getName());
        //不应该在这里设置token 因为会导致自动登录 后 的用户信息与创建的信息不一致
        /**
         * 如果之前登录过A的信息
         * 在创建B车队  没有车辆信息
         * 重新自动登录后 由于已经有token了 会自动跳转到主界面  用的信息还是之前选择的车辆信息
         *
         * 解决办法 不在这里设置token 在车辆选择界面设置token 车辆选择成功后才可以
         */
        mPresenter.setToken(token);
        Intent intent = new Intent(this, CarListActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void gotoCreateProject() {

    }

    @Override
    public void changeToCreateProject(String errorMsg) {
        dismissProcessDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(errorMsg)
                .setTitle("请先创建车队")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        etVerification.setText("");
                        //打开创建界面
                        Intent intent = new Intent(mContext,CreatProject.class);
                        mContext.startActivity(intent);
                    }
                });
        builder.show();
    }

    /**
     * 显示loading 登录中
     */
    @Override
    public void showToLogin() {
        showProcessDialog("创建成功，登录中...");
        if (postingString == null) {
            dismissProcessDialog();
            return;
        }
        mPresenter.login2(postingString);
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
        } else {
            if (!SystemUtil.isChinaPhoneLegal(phone)) {
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
        // json传递
        postingString = new LoginEntity(phone, verification, projectName);


        /**
         * 存储登录信息
         * 手机号 车队名称
         */
        LoginInfo loginInfo = new LoginInfo("", phone, projectName);
        mPresenter.inserLoginInfo(loginInfo);
        mPresenter.login(postingString);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_get_ver:
                //            获取验证码
                long startTime = 0;
                try {
                     startTime = SPUtils.getInstance().getLong(KEY_START_TIME, 0L);
                }catch (Exception e) {
                    e.printStackTrace();
                }

                phone = etPhone.getText().toString().trim();
                long currentTime = System.currentTimeMillis();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showShort("手机号不能为空");
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

                currentSecond = COUNTDOWN;
                handler.sendEmptyMessage(0);

                break;
            case R.id.tv_createProejct:
                //新建车队

                Intent intent = new Intent(mContext,CreatProject.class);
                mContext.startActivity(intent);
                break;
            default:
        }
    }

    /**
     * 刷新时间
     */
    @Override
    public void updataVerifi() {


    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (tvGetVer != null) {
                if (currentSecond > 0) {

                    //更新 验证码计时显示
                    tvGetVer.setText("剩余" + currentSecond + "s");
                    tvGetVer.setEnabled(false);
                    //每次减少1秒
                    currentSecond--;
                    //延迟一秒执行
                    handler.sendEmptyMessageDelayed(0, 1000);
                } else {
                    //恢复默认
                    tvGetVer.setText("重新发送");
                    tvGetVer.setEnabled(true);
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setExtraData(intent);
    }

    private void setExtraData(Intent intent) {
        String temp_projectName = intent.getStringExtra(Constants.TEMP_PROJECT_NAME);
        String temp_phoneNum = intent.getStringExtra(Constants.TEMP_USER_PHONE_NUM);
        if (!TextUtils.isEmpty(temp_projectName) && !TextUtils.isEmpty(temp_phoneNum)) {
            etProjectName.setText(temp_projectName);
            etPhone.setText(temp_phoneNum);
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }

            tvGetVer.setEnabled(true);
            tvGetVer.setText("重新发送");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
