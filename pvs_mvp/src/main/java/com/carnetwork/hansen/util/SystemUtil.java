package com.carnetwork.hansen.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.carnetwork.hansen.app.Constants;
import com.carnetwork.hansen.app.MyApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * author：Sun on 2017/8/24/0024.
 * email：1564063766@qq.com
 * remark:系统工具类
 */
public class SystemUtil {

    public SystemUtil() {
    }

    public static SystemUtil getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static SystemUtil instance = new SystemUtil();
    }


    /**
     * 检查WIFI是否连接
     */
    public static boolean isWifiConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifiInfo != null;
    }

    /**
     * 检查手机网络(4G/3G/2G)是否连接
     */
    public static boolean isMobileNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileNetworkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return mobileNetworkInfo != null;
    }

    /**
     * 检查是否有可用网络
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }


    /**
     * 保存图片到本地
     *
     * @param context
     * @param url
     * @param bitmap
     */
    public static Uri saveBitmapToFile(Context context, String url, Bitmap bitmap, View container, boolean isShare) {
        String fileName = url.substring(url.lastIndexOf("/"), url.lastIndexOf(".")) + ".png";
        File fileDir = new File(Constants.PATH_SDCARD);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File imageFile = new File(fileDir, fileName);
        Uri uri = Uri.fromFile(imageFile);
        if (isShare && imageFile.exists()) {
            return uri;
        }
        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            boolean isCompress = bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
            if (isCompress) {

            } else {

            }
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), imageFile.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        return uri;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int dp2px(float dpValue) {
        final float scale = MyApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int px2dp(float pxValue) {
        final float scale = MyApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    public static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 手机IMEI
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {
            return tm.getDeviceId();
        }
        return null;
    }

    /**
     * 窗体变亮
     */
    public void windowToLight(Activity activity) {
        try {
            if (activity == null) {
                return;
            }
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.alpha = 1f;
            lp.dimAmount = 1f;
            activity.getWindow().setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 窗体变暗
     */
    public void windowToDark(Activity activity) {
        try {
            if (activity == null) {
                return;
            }
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.alpha = 0.4f;
            lp.dimAmount = 0.4f;
            activity.getWindow().setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private long lastClickTime;

    /**
     * 避免按钮在短时间内，被多次点击，执行多次任务
     *
     * @return
     */
    public boolean isFastClick() {
        long currentClickTime = SystemClock.elapsedRealtime();
        if ((currentClickTime - lastClickTime) >= 1000) {
            return false;
        }
        lastClickTime = currentClickTime;
        return true;
    }

    /**
     * 获取对应百分比的字体大小
     */
    public int getPercentTextSize(Context context, int textSize) {
        int screenHeight = ScreenUtils.getScreenHeight(context);
        int screenWidth = ScreenUtils.getScreenWidth(context);
        float ratioWidth = (float) screenWidth / 720;  //  默认720作为基础宽
        float ratioHeight = (float) screenHeight / 1280; //默认1280作为基础高
        float mTextSizePercent = Math.min(ratioWidth, ratioHeight);
        return Math.round(textSize * mTextSizePercent);
    }

    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        // ^ 匹配输入字符串开始的位置
        // \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
        // $ 匹配输入字符串结尾的位置
        String regExp = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])" +
                "|(18[0-9])|(19[8,9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static String iso2utf(String isoStr){
        String utfStr = null;
        try {
// 入参汉字的转码
            utfStr = new String(isoStr.getBytes("ISO-8859-1"), "UTF-8");

        } catch (UnsupportedEncodingException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return utfStr;
    }
    public static String iso2gbk(String isoStr){
        String utfStr = null;
        try {
// 入参汉字的转码
            utfStr = new String(isoStr.getBytes("ISO-8859-1"), "GBK");

        } catch (UnsupportedEncodingException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return utfStr;
    }
}
