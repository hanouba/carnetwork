package com.carnetwork.hansen.ui.main.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.carnetwork.hansen.R;
import com.carnetwork.hansen.app.Constants;
import com.carnetwork.hansen.base.BaseActivity;
import com.carnetwork.hansen.mvp.contract.main.CarListContract;
import com.carnetwork.hansen.mvp.model.bean.CarCreateEnity;
import com.carnetwork.hansen.mvp.model.bean.CarListBean;
import com.carnetwork.hansen.mvp.model.http.NetWorkChangReceiver;
import com.carnetwork.hansen.mvp.presenter.main.CarListPresenter;
import com.carnetwork.hansen.ui.main.adapter.CarListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 車輛列表
 * 選擇一個車輛
 * 或者創建一個新的車輛
 */
public class CarListActivity extends BaseActivity<CarListPresenter> implements CarListContract.View {


    @BindView(R.id.iv_add_new)
    ImageView ivAddNew;
    @BindView(R.id.rv_car_list)
    RecyclerView rvCarList;
    @BindView(R.id.bt_next)
    Button btNext;


    private CarListAdapter mCarListAdapter;
    @Override
    protected int getLayout() {
        return R.layout.activity_car_list;
    }

    @Override
    protected void initEventAndData() {
        showProcessDialog("");
        String token = SPUtils.getInstance().getString(Constants.TOKEN);
        mPresenter.getCarList(token);



    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }



    @OnClick({R.id.iv_add_new, R.id.bt_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_add_new:
                //添加車輛

                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("添加车辆");
                View addCarView = getLayoutInflater().inflate(R.layout.item_car_create,null);
                EditText etCarNo = addCarView.findViewById(R.id.et_car_no);
                EditText etCarLicence = addCarView.findViewById(R.id.et_car_licence);
                EditText etCreatePhone = addCarView.findViewById(R.id.et_create_phone);

                builder.setView(addCarView);//设置login_layout为对话提示框
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String carNo  = etCarNo.getText().toString().trim();
                        String carLicence = etCarLicence.getText().toString().trim();
                        String creatPhone = etCreatePhone.getText().toString().trim();
                        if (TextUtils.isEmpty(carNo) || TextUtils.isEmpty(carLicence) ||TextUtils.isEmpty(creatPhone)) {
                            ToastUtils.showShort("信息填写不全");
                        }else {
                            long projectId = SPUtils.getInstance().getLong(Constants.PROJECT_PROJECTID);

                            CarCreateEnity carCreateEnity = new CarCreateEnity(carLicence,carNo,creatPhone,String.valueOf(projectId));
                            mPresenter.createCar(carCreateEnity);
                        }
                    }
                });
                //设置反面按钮，并做事件处理
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();//显示Dialog对话框

                break;
            case R.id.bt_next:
                //下一步 跳轉到主界面
                break;
        }
    }

    /**
     * 錯誤信息顯示 隱藏加載界面
     * @param carListBeans
     */
    @Override
    public void showCarList(List<CarListBean> carListBeans) {
        dismissProcessDialog();
        if (mCarListAdapter == null) {
            mCarListAdapter = new CarListAdapter(carListBeans);
            rvCarList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            rvCarList.setAdapter(mCarListAdapter);
        }else {
            mCarListAdapter.setNewInstance(carListBeans);
        }




    }

    @Override
    public void showErrorMsg(String msg) {

        dismissProcessDialog();
        ToastUtils.showShort(msg);
    }
}