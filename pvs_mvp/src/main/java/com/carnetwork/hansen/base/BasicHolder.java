package com.carnetwork.hansen.base;

import android.view.View;

/**
 * author：Sun on 2017/8/25/0025.
 * email：1564063766@qq.com
 * remark:Holder基类
 */
public abstract class BasicHolder<T> {
    public View holderView;//注意：一开始就将convertView转移到holder中用一个变量表示

    public BasicHolder() {
        //1.初始化holderView
        holderView = initHolderView();//需要holderView

        //2.设置tag
        holderView.setTag(this);
    }

    /**
     * 初始化holderView
     *
     * @return
     */
    public abstract View initHolderView();

    /**
     * 绑定数据
     */
    public abstract void bindData(T data);

    /**
     * 获取holderView
     *
     * @return
     */
    public View getHolderView() {
        return holderView;
    }
}
