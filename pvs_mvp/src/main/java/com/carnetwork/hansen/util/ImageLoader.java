package com.carnetwork.hansen.util;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;



/**
 * 创建者 by ${HanSir} on 2018/12/24.
 * 版权所有  WELLTRANS.
 * 说明
 */

public class ImageLoader {

    public static void load(Activity activity, String url, ImageView iv) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        if (!activity.isDestroyed()) {
//            Glide.with(activity).load(url).crossFade().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv);
            Glide.with(activity).load(url).into(iv);
        }
    }

    public static void load(Context context, Object url, ImageView iv) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        Glide.with(context).load(url).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
    }

    public static void load(Activity activity, Object url, ImageView iv) {    //使用Glide加载圆形ImageView(如头像)时，不要使用占位图
        if (!activity.isDestroyed()) {
            Glide.with(activity).load(url).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(iv);
        }
    }





}
