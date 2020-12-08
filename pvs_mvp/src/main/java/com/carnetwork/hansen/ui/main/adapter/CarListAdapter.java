package com.carnetwork.hansen.ui.main.adapter;

import android.view.View;

import com.blankj.utilcode.util.SPUtils;
import com.carnetwork.hansen.R;
import com.carnetwork.hansen.app.Constants;
import com.carnetwork.hansen.mvp.model.bean.CarListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CarListAdapter extends BaseQuickAdapter<CarListBean, BaseViewHolder> {
    private SubClickListener clickListener;
    private int mposition = -1;
    public CarListAdapter( @Nullable List<CarListBean> data) {
        super(R.layout.item_car_list, data);


    }

    public void setClickListener(SubClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CarListBean carListBean) {
        baseViewHolder.setText(R.id.text_car_no,carListBean.getCarNo());
        baseViewHolder.setText(R.id.text_car_licence,carListBean.getCarLicence());
        int adapterPosition = baseViewHolder.getAdapterPosition();
        if (adapterPosition == mposition) {
            baseViewHolder.setVisible(R.id.DeviceList_Select,true);
        } else {
            baseViewHolder.setVisible(R.id.DeviceList_Select,false);
        }



        baseViewHolder.getView(R.id.rl_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onSubClickListener(carListBean.getCarNo());
                SPUtils.getInstance().put(Constants.CAR_NO,carListBean.getCarNo());
                SPUtils.getInstance().put(Constants.CAR_LICENCE,carListBean.getCarLicence());
                mposition = baseViewHolder.getAdapterPosition();
                baseViewHolder.setVisible(R.id.DeviceList_Select,true);
                notifyDataSetChanged();
            }
        });
    }

    public interface SubClickListener  {
        void onSubClickListener(String carNo);
    }

}
