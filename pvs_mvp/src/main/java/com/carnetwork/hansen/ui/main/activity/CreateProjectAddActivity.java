package com.carnetwork.hansen.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.carnetwork.hansen.R;
import com.carnetwork.hansen.app.Constants;
import com.carnetwork.hansen.base.BaseActivity;
import com.carnetwork.hansen.mvp.contract.main.LoginContract1;
import com.carnetwork.hansen.mvp.model.bean.LoginBean;
import com.carnetwork.hansen.mvp.model.bean.ProjectEntity;
import com.carnetwork.hansen.mvp.model.db.LoginInfo;
import com.carnetwork.hansen.mvp.presenter.main.LoginPresenter1;
import com.carnetwork.hansen.util.SystemUtil;

import java.util.List;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.carnetwork.hansen.app.Constants.KEY_START_TIME;

/**
 * 添加用户界面
 */
public class CreateProjectAddActivity extends BaseActivity<LoginPresenter1>
        implements LoginContract1.View {


    @BindView(R.id.iv_back)
    ImageButton ivBack;
    @BindView(R.id.info_title_name)
    TextView infoTitleName;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.iv_step)
    ImageView ivStep;
    @BindView(R.id.tv_create_step1)
    TextView tvCreateStep1;
    @BindView(R.id.tv_create_step1_tip)
    TextView tvCreateStep1Tip;
    @BindView(R.id.tv_create_step2)
    TextView tvCreateStep2;
    @BindView(R.id.tv_create_step2_tip)
    TextView tvCreateStep2Tip;
    @BindView(R.id.tv_create_step3)
    TextView tvCreateStep3;
    @BindView(R.id.tv_create_step3_tip)
    TextView tvCreateStep3Tip;
    @BindView(R.id.rl_step)
    RelativeLayout rlStep;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.rl_selected_user)
    RelativeLayout rlSelectedUser;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_phone_num)
    TextView tvPhoneNum;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.tv_admin)
    TextView tvAdmin;
    @BindView(R.id.et_verification)
    EditText etVerification;
    @BindView(R.id.tv_get_ver)
    AppCompatTextView tvGetVer;
    @BindView(R.id.rl_admin)
    RelativeLayout rlAdmin;
    @BindView(R.id.line3)
    View line3;
    @BindView(R.id.btn_continue_add)
    Button btnContinueAdd;
    @BindView(R.id.bt_next)
    Button btNext;
    //车队名称
    private String motoName = "";
    private String phoneNumber = "";
    /**
     * 计时器 倒计时60秒
     */
    private int currentSecond;
    //总时间60秒
    private static final int COUNTDOWN = 60;
    private String userName ="";

    @Override
    protected int getLayout() {
        return R.layout.activity_create_project_add;
    }

    @Override
    protected void initEventAndData() {
        motoName = getIntent().getStringExtra(Constants.PROJECT_NAME);

        infoTitleName.setText("创建用户");

    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);

    }

    @Override
    public void showErrorDialog(String msg) {
        dismissProcessDialog();
        ToastUtils.showShort(msg);
    }

    @Override
    public void gotoMainActivity(LoginBean loginBean) {

    }

    @Override
    public void gotoCreateProject() {

    }

    @Override
    public void changeToCreateProject(String errorMsg) {

    }

    /**
     * 创建车队成功
     * 跳转到登录界面
     */
    @Override
    public void showToLogin() {


        Intent intent = new Intent(mContext, CreateProjectSuccActivity.class);
        intent.putExtra(Constants.PROJECT_NAME, motoName);
        intent.putExtra(Constants.USER_PHONE_NUM, phoneNumber);
        startActivityForResult(intent, 1000);
    }

    @Override
    public void loginFail(String msg) {

    }

    @Override
    public void updataVerifi() {

    }


    @OnClick({R.id.iv_back, R.id.tv_get_ver, R.id.btn_continue_add, R.id.bt_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_get_ver:
                //获取验证码
                long startTime = SPUtils.getInstance().getLong(KEY_START_TIME, 0);
                phoneNumber = etPhoneNumber.getText().toString().trim();
                long currentTime = System.currentTimeMillis();
                if (phoneNumber == null) {
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
                mPresenter.getMessageCode(phoneNumber);

                currentSecond = COUNTDOWN;
                handler.sendEmptyMessage(0);
                break;
            case R.id.btn_continue_add:
                //继续添加
                break;
            case R.id.bt_next:
                // 下一步
                userName = etName.getText().toString().trim();
                String verification = etVerification.getText().toString().trim();

                if (TextUtils.isEmpty(userName)) {
                    ToastUtils.showLong("姓名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(phoneNumber)) {
                    ToastUtils.showLong("手机号不能为空");

                    return;
                } else {
                    if (!SystemUtil.isChinaPhoneLegal(phoneNumber)) {
                        ToastUtils.showLong("手机号格式不对");
                        return;
                    }
                }
                if (TextUtils.isEmpty(verification)) {
                    ToastUtils.showLong("验证码不能为空");
                    return;
                }
                showProcessDialog("创建中...");
                ProjectEntity projectEntity = new ProjectEntity(verification, phoneNumber, motoName, userName);

                LoginInfo loginInfo = new LoginInfo(userName, phoneNumber, motoName);
                mPresenter.inserLoginInfo(loginInfo);
                mPresenter.createProject(projectEntity);
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
}