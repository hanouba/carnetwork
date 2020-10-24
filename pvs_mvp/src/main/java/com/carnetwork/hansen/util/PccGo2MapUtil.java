package com.carnetwork.hansen.util;

import android.content.Intent;
import android.net.Uri;


import com.amap.api.maps.model.LatLng;
import com.blankj.utilcode.util.ToastUtils;
import com.carnetwork.hansen.R;

import java.io.File;

import static com.blankj.utilcode.util.ActivityUtils.startActivity;


/**
 * Create by HanN on 2019/5/29
 * 注释:
 */
public class PccGo2MapUtil {


        public static final String PN_GAODE_MAP = "com.autonavi.minimap";// 高德地图包名
        public static final String PN_BAIDU_MAP = "com.baidu.BaiduMap"; // 百度地图包名
        public static final String PN_TENCENT_MAP = "com.tencent.map"; // 腾讯地图包名

        /**
         * 检查地图应用是否安装
         * @return
         */
        public static boolean isGdMapInstalled(){
            return isInstallPackage(PN_GAODE_MAP);
        }

        public static boolean isBaiduMapInstalled(){
            return isInstallPackage(PN_BAIDU_MAP);
        }

        public static boolean isTencentMapInstalled(){
            return isInstallPackage(PN_TENCENT_MAP);
        }

        private static boolean isInstallPackage(String packageName) {
            return new File("/data/data/" + packageName).exists();
        }

        /**
         * 百度转高德
         * @param bd_lat
         * @param bd_lon
         * @return bd_lat_lon[0] 经度  bd_lat_lon[1] 纬度
         */
        public static double[] bdToGaoDe(double bd_lat, double bd_lon) {
            double[] gd_lat_lon = new double[2];
            double PI = 3.14159265358979324 * 3000.0 / 180.0;
            double x = bd_lon - 0.0065, y = bd_lat - 0.006;
            double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * PI);
            double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * PI);
            gd_lat_lon[0] = z * Math.cos(theta);
            gd_lat_lon[1] = z * Math.sin(theta);
            return gd_lat_lon;
        }

        /**
         * 高德、腾讯转百度
         * @param gd_lon
         * @param gd_lat
         * @return  bd_lat_lon[0] 经度  bd_lat_lon[1] 纬度
         */
        public static double[] gaoDeToBaidu(double gd_lon, double gd_lat) {
            double[] bd_lat_lon = new double[2];
            double PI = 3.14159265358979324 * 3000.0 / 180.0;
            double x = gd_lon, y = gd_lat;
            double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * PI);
            double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * PI);
            bd_lat_lon[0] = z * Math.cos(theta) + 0.0065;
            bd_lat_lon[1] = z * Math.sin(theta) + 0.006;
            return bd_lat_lon;
        }

        /**
         * 百度坐标系 (BD-09) 与 火星坐标系 (GCJ-02)的转换
         * 即 百度 转 谷歌、高德
         *
         * @param latLng
         * @returns
         *
         * 使用此方法需要下载导入百度地图的BaiduLBS_Android.jar包
         */
        public static LatLng BD09ToGCJ02(LatLng latLng) {
            double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
            double x = latLng.longitude - 0.0065;
            double y = latLng.latitude - 0.006;
            double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
            double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
            double gg_lat = z * Math.sin(theta);
            double gg_lng = z * Math.cos(theta);
            return new LatLng(gg_lat, gg_lng);
        }

        /**
         * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换
         * 即谷歌、高德 转 百度
         *
         * @param latLng
         * @returns
         *
         * 需要百度地图的BaiduLBS_Android.jar包
         */
        public static LatLng GCJ02ToBD09(LatLng latLng) {
            double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
            double z = Math.sqrt(latLng.longitude * latLng.longitude + latLng.latitude * latLng.latitude) + 0.00002 * Math.sin(latLng.latitude * x_pi);
            double theta = Math.atan2(latLng.latitude, latLng.longitude) + 0.000003 * Math.cos(latLng.longitude * x_pi);
            double bd_lat = z * Math.sin(theta) + 0.006;
            double bd_lng = z * Math.cos(theta) + 0.0065;
            return new LatLng(bd_lat, bd_lng);
        }

        /**
         * 打开高德地图导航功能

         * @param dlat 终点纬度
         * @param dlon 终点经度
         * @param dname 终点名称 必填
         */
        public static void openGaoDeNavi( double dlat, double dlon, String dname){

//            Uri uri = Uri.parse("androidamap://navi?sourceApplication=appname&amp;poiname=fangheng&amp;lat=36.547901&amp;lon=104.258354&amp;dev=1&amp;style=2");
//            Uri uri = Uri.parse("androidamap://navi?sourceApplication=appname&amp;poiname=fangheng&amp;lat=36.547901&amp;lon=104.258354&amp;dev=1&amp;style=2");
            if (isGdMapInstalled()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setPackage("com.autonavi.minimap");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse("androidamap://route?sourceApplication=" + R.string.app_name
                        + "&sname=我的位置&dlat=" + dlat
                        + "&dlon=" + dlon
                        + "&dname=" + dname
                        + "&dev=0&m=0&t=1"));
                startActivity(intent);
            } else {
                ToastUtils.showShort( "高德地图未安装");
            }
        }

        /**
         * 打开百度地图
         * params 参考http://lbs.qq.com/uri_v1/guide-route.html
         *
         * mode = transit（公交）、driving（驾车）、walking（步行）和riding（骑行）. 默认:driving
         * 当 mode=transit 时 ： sy = 0：推荐路线 、 2：少换乘 、 3：少步行 、 4：不坐地铁 、 5：时间短 、 6：地铁优先
         *
         * @param dlat  终点纬度
         * @param dlon  终点经度
         * @param dname 终点名称
         */
        public static void openBaiDuMap(double dlat, double dlon, String dname) {
            if (isBaiduMapInstalled()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("baidumap://map/direction?origin=我的位置&destination=name:"
                        + dname
                        + "|latlng:" + dlat + "," + dlon
                        + "&mode=driving&sy=3&index=0&target=1"));
                startActivity(intent);
            } else {
                ToastUtils.showShort("百度地图未安装");
            }
        }

        /**
         * 打开腾讯地图（公交出行，起点位置使用地图当前位置）
         *
         * 公交：type=bus，policy有以下取值
         * 0：较快捷 、 1：少换乘 、 2：少步行 、 3：不坐地铁
         * 驾车：type=drive，policy有以下取值
         * 0：较快捷 、 1：无高速 、 2：距离短
         * policy的取值缺省为0
         *
         * @param dlat  终点纬度
         * @param dlon  终点经度
         * @param dname 终点名称
         */
        public static void openTencentNavi(double dlat, double dlon, String dname){
            if (isTencentMapInstalled()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("qqmap://map/routeplan?type=bus&from=我的位置&fromcoord=0,0"
                        + "&to=" + dname
                        + "&tocoord=" + dlat + "," + dlon
                        + "&policy=1&referer=myapp"));
                startActivity(intent);
            } else {
                ToastUtils.showShort("腾讯地图未安装");
            }
        }



}
