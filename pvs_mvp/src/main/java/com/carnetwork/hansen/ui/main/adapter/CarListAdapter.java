package com.carnetwork.hansen.ui.main.adapter;

import com.carnetwork.hansen.mvp.model.bean.CarListBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CarListAdapter extends BaseQuickAdapter<CarListBean, BaseViewHolder> {
    public CarListAdapter(int layoutResId, @Nullable List<CarListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CarListBean carListBean) {

    }
}
