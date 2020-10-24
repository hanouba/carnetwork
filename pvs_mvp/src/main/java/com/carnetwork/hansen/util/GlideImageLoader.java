package com.carnetwork.hansen.util;

import android.content.Context;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Create by Administrator on 2019/1/14
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);

    }
}
