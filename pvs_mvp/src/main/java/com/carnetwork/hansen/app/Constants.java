package com.carnetwork.hansen.app;

import android.os.Environment;

import java.io.File;

/**

 * remark:  常量池
 */
public class Constants {
    public static final String GREEN_DB ="car_net.db";
    //================= PATH ====================

    public static final String PATH_DATA = MyApplication.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";

    public static final String PATH_CACHE = PATH_DATA + "/NetCache";

    public static final String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "com.carnetwork.hansen";
    public static final String PATH_SDCARD_DCIM = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "DCIM";
    public static final String DownloadPath = PATH_SDCARD + File.separator + "download";
    public static final String TAKE_PHOTO_PATH = PATH_SDCARD + File.separator + "takePhoto";
    public static final String PATH_DOWNLOAD_VIDEO = PATH_SDCARD + File.separator + "download" + File.separator + "video";

    public static final String BUGLY_ID = "db2fd2e040";
    public static final String DATABASE_USER_NAME = "car_net_hansen";




//    xinix

    public static final String CAR_NO ="carno";
    public static final String CAR_NAME ="carname";
    public static final String CAR_PHONE ="carphone";
    public static final String CAR_LICENCE ="carlicence";
    public static final String IS_ON_WORK ="isonwork";
    public static  final String  TOKEN ="token";




}
