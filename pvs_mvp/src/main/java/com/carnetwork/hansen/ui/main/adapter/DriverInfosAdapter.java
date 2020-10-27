package com.carnetwork.hansen.ui.main.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.carnetwork.hansen.R;
import com.carnetwork.hansen.mvp.model.bean.AllDrvier;

import java.util.List;

public class DriverInfosAdapter extends RecyclerView.Adapter<DriverInfosAdapter.ViewHolder> {
    private List<AllDrvier.ModelBean> mDriverList;

    public DriverInfosAdapter(List<AllDrvier.ModelBean> mDriverList) {
        this.mDriverList = mDriverList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_driver_info,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AllDrvier.ModelBean driver = mDriverList.get(position);
        holder.driverNo.setText(driver.getCarNum());
        holder.driverLicence.setText(driver.getCarLicence());
        holder.driverName.setText(driver.getName());
        holder.driverPhone.setText(driver.getPhone());
    }

    @Override
    public int getItemCount() {
        return mDriverList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView driverNo;
        TextView driverName;
        TextView driverLicence;
        TextView driverPhone;

        public ViewHolder (View view)
        {
            super(view);
            driverNo = (TextView) view.findViewById(R.id.tv_driver_carno);
            driverPhone = (TextView) view.findViewById(R.id.tv_driver_phone);
            driverLicence = (TextView) view.findViewById(R.id.tv_driver_carlicence);
            driverName = (TextView) view.findViewById(R.id.tv_driver_name);
        }

    }
}
