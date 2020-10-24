package com.carnetwork.hansen.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * author：Sun on 2017/8/25/0025.
 * email：1564063766@qq.com
 * remark:Adapter基类
 */
public abstract class BasicAdapter<T> extends BaseAdapter {
    protected ArrayList<T> list;

    public BasicAdapter(ArrayList<T> list) {
        super();
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1.初始化holder
        BasicHolder<T> holder = null;
        if (convertView == null) {
            holder = getHolder(position);// 需要一个不固定的holder
        } else {
            holder = (BasicHolder) convertView.getTag();
        }
        // 3.绑定数据
        holder.bindData(list.get(position));
        return holder.getHolderView();
    }

    protected abstract BasicHolder<T> getHolder(int position);

}
