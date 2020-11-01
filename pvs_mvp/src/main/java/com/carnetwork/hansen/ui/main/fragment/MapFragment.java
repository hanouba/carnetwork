package com.carnetwork.hansen.ui.main.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.carnetwork.hansen.R;
import com.carnetwork.hansen.app.Constants;
import com.carnetwork.hansen.app.MyApplication;
import com.carnetwork.hansen.base.BaseFragment;
import com.carnetwork.hansen.component.keepalive.NoVoiceService;
import com.carnetwork.hansen.mvp.contract.main.MapContract;
import com.carnetwork.hansen.mvp.model.bean.AllCar;
import com.carnetwork.hansen.mvp.model.bean.SateBean;
import com.carnetwork.hansen.mvp.model.bean.UploadMapEntity;
import com.carnetwork.hansen.mvp.presenter.main.MapPresenter;
import com.carnetwork.hansen.ui.main.activity.SellectActivity;
import com.carnetwork.hansen.widget.CustomPopupWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author HanN on 2020/10/23 10:06
 * @email: 1356548475@qq.com
 * @project carnetwork
 * @description: 地图
 * @updateuser:
 * @updatedata: 2020/10/23 10:06
 * @updateremark:
 * @version: 2.1.67
 */
public class MapFragment extends BaseFragment<MapPresenter> implements MapContract.View {

    @BindView(R.id.map)
    MapView mMapView;
    @BindView(R.id.bt_reset)
    ImageButton mBtReset;
    @BindView(R.id.tv_work_state)
    TextView tvWorkState;
    @BindView(R.id.bt_newAddEnd)
    AppCompatButton btNewAddEnd;
    @BindView(R.id.bt_newAddStart)
    AppCompatButton btNewAddStart;
    private List<AllCar> carLists;

    private BaiduMap mBaiduMap;

    private boolean isFirstLocation = true;

    public static String sateType = "1";

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();
    private LocationClientOption option = new LocationClientOption();
    private String carNo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        initview(savedInstanceState, view);
        return view;

    }

    private void initview(Bundle savedInstanceState, View view) {
        mMapView = (MapView) view.findViewById(R.id.map);
        if (mBaiduMap == null) {
            mBaiduMap = mMapView.getMap();
        }
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(18.0f);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        mBaiduMap.setMyLocationEnabled(true);
    }


    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_map;
    }


    @Override
    protected void initEventAndData() {
        //定位显示showAllCar
        location();

        carNo = SPUtils.getInstance().getString(Constants.CAR_NO);


        String token = SPUtils.getInstance().getString(Constants.TOKEN);
        //获取所以车联信息
        mPresenter.getAllCar(token, carNo);
        //获取所有的起点终点
        mPresenter.getSateList(carNo);


        //        显示工作状态
        showcurrentWorkState();

        //        启动后台服务
        Intent intent = new Intent(getActivity(), NoVoiceService.class);
        getActivity().startService(intent);

        //自定义marker弹窗
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                if (marker.getTitle().contains("car")) {
                    showCarInfosBottom(marker);
//                    String title = marker.getTitle();
//                    String[] split = title.split(",");
//                    callPhone(split[2]);
                }else {

                    showExitDialog(marker.getTitle().split(",")[0]);

                }



                return false;
            }
        });




        //不显示缩放按钮
        mMapView.showZoomControls(false);

        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }

            @Override
            public void onMapPoiClick(MapPoi mapPoi) {
            }
        });
    }

    private void showExitDialog(String id) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("是否删除");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                    mPresenter.deleateSate(carNo,id);

            }
        });
        builder.show();
    }

    /**
     * 显示底部车辆信息
     * @param marker
     */
    private void showCarInfosBottom(Marker marker) {
        int halfWidth = 0;
        Window window = getActivity().getWindow();
        if (window != null) {
            Display d = window.getWindowManager().getDefaultDisplay();  //为获取屏幕宽、高
            halfWidth = (int) (d.getWidth() * 0.95);//屏幕宽度的0.9
        }

        CustomPopupWindow bottomCarInfo = new CustomPopupWindow.Builder().
                setContext(getContext())
                .setContentView(R.layout.car_info_bottom_window)
                .setwidth(halfWidth)
                .setheight(LinearLayout.LayoutParams.WRAP_CONTENT)
                .setFouse(true)
                .setOutSideCancel(true)
                .setAnimationStyle(R.style.mypopwindow_anim_style)
                .setBackGroudAlpha(getActivity(), 0)
                .builder()
                .showAtLocation(mMapView, Gravity.BOTTOM, 0, -200);

        TextView tvCarLicence = (TextView) bottomCarInfo.getItemView(R.id.tv_bottom_car_licence);
        TextView tvCarState = (TextView) bottomCarInfo.getItemView(R.id.tv_bottom_car_state);
        TextView tvCarTime = (TextView) bottomCarInfo.getItemView(R.id.tv_reprot_time);
        TextView tvCarSpeed = (TextView) bottomCarInfo.getItemView(R.id.tv_speed);
        ImageView ivCarPhone = (ImageView) bottomCarInfo.getItemView(R.id.iv_car_phone);
        // 1车辆编号  2车辆 车牌号 3 电话号码  4 speed  5状态 6 time stop等信息
        String title = marker.getTitle();
        String[] split = title.split(",");

        tvCarLicence.setText(split[1]);
        tvCarSpeed.setText(split[3]);
        if ("stop".equals(split[4])) {
            tvCarState.setText("停止");
            tvCarState.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.state_car_red));
        }else {
            tvCarState.setText("行驶");
            tvCarState.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.state_car_green));
        }
        tvCarTime.setText(split[5]);
        ivCarPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone(split[2]);
            }
        });
    }


    /**
     * 在地图上显示当前工作状态
     */
    private void showcurrentWorkState() {
        boolean work = SPUtils.getInstance().getBoolean(Constants.IS_ON_WORK);
        if (work) {

            tvWorkState.setText("工作中...");
            tvWorkState.setBackground(ContextCompat.getDrawable(getActivity(), R.color.color_3BD134));
        } else {
            tvWorkState.setText("休息中...");
            tvWorkState.setBackground(ContextCompat.getDrawable(getActivity(), R.color.grey8));
        }
    }






    /**
     * 定位到当前位置
     */
    private void showCurrentLocation(BaiduMap map, BDLocation bdLocation, Boolean isShowLoc) {
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(bdLocation.getRadius())
                .direction(bdLocation.getRadius()).latitude(bdLocation.getLatitude())
                .longitude(bdLocation.getLongitude()).build();
        map.setMyLocationData(locData);

        if (isShowLoc) {
            LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(18.0f);
            map.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.bt_reset)
    public void onViewClicked() {
        isFirstLocation = true;
    }


    /**
     * 显示所有的的车辆 和基本信息
     * @param allCars
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void showAllCar(List<AllCar> allCars) {

//        if (carLists == null) {
//            carLists = new ArrayList<>();
//        }
//        carLists.clear();
//
//        carLists = new ArrayList<>(allCars);
        if (allCars.size() < 1) {
            return;
        }

        View view = View.inflate(getActivity(), R.layout.custom_info_window, null);
        LinearLayout popView = view.findViewById(R.id.cl_root);
        TextView tvCarno = view.findViewById(R.id.tv_map_car_no);
        TextView tvLicence = view.findViewById(R.id.tv_car_map_licence);

        //循坏在地图上添加自定义marker
        for (int i = 0; i < allCars.size(); i++) {
            AllCar currentCar = allCars.get(i);
            // 1车辆编号  2车辆 车牌号 3 电话号码  4 speed  5状态 6 timestop等信息
            String title = currentCar.getCarNum()+","+currentCar.getCarLicence()+","+currentCar.getPhone()+
                    ","+currentCar.getSpeed()+","+currentCar.getStatus() +","+currentCar.getReportTime()+"car";
            if ("stop".equals(currentCar.getStatus())) {
                popView.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.border_red));
            }else {
                popView.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.border_green));
            }
            tvCarno.setText("车辆编号:  "+currentCar.getCarNum());
            tvLicence.setText("车辆牌照:  "+currentCar.getCarLicence());

            //定义Maker坐标点
            LatLng point = new LatLng(Double.parseDouble(currentCar.getLat()), Double.parseDouble(currentCar.getLon()));
            BitmapDescriptor bd1 = BitmapDescriptorFactory.fromBitmap(getBitmapFromView(view));
            MarkerOptions ooA = new MarkerOptions().position(point).icon(bd1).zIndex(9).draggable(true).title(title);
            //在地图上添加Marker，并显示
            mBaiduMap.addOverlay(ooA);


        }
    }

    /**
     * 将view布局转换成 bitmap
     * @param view
     * @return
     */
    private Bitmap getBitmapFromView(View view) {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.UNSPECIFIED);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }
    /**
     * 显示所以的 起点终点
     * @param sateBeans
     */
    @Override
    public void showAllSate(List<SateBean> sateBeans) {
        //循坏在地图上添加自定义marker
        if (sateBeans.size() < 1) {
            return;
        }
        mBaiduMap.clear();

        View view = View.inflate(getActivity(), R.layout.item_sate_mark_view, null);
        LinearLayout popView = view.findViewById(R.id.cl_root);
        TextView tvSateName = view.findViewById(R.id.tv_sate_name);
        ImageView ivSateMark = view.findViewById(R.id.iv_sate_mark);

        for (int i = 0; i < sateBeans.size(); i++) {
            //定义Maker坐标点
            double lat = Double.parseDouble(sateBeans.get(i).getLat());
            double lon = Double.parseDouble(sateBeans.get(i).getLon());
            LatLng point = new LatLng(lat, lon);
            int sateresource = R.drawable.icon_loc;
            String sateType = "磅房: ";
            if (sateBeans.get(i).getSateType() == 1) {
                popView.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.border_green));
                 sateresource = R.drawable.icon_loc_green;
                sateType = "磅房: ";
            }else if (sateBeans.get(i).getSateType() == 2) {
                sateresource = R.drawable.gps_orange;
                popView.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.border_orange));
                sateType = "码头: ";
            }else {
                sateType = "公司: ";
                sateresource = R.drawable.icon_loc_red;
                popView.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.border_red));
            }
            tvSateName.setText(sateType+sateBeans.get(i).getSateName());


            BitmapDescriptor bd1 = BitmapDescriptorFactory.fromBitmap(getBitmapFromView(view));
            MarkerOptions ooA = new MarkerOptions().position(point).icon(bd1).zIndex(9).draggable(true).title(sateBeans.get(i).getId()+","+"sate");
            //在地图上添加Marker，并显示
            mBaiduMap.addOverlay(ooA);



        }
    }

    @Override
    public void changeWorkState() {
        showcurrentWorkState();
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }


    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
        //获取所有的起点终点
        mPresenter.getSateList(carNo);
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
    }

    @Override
    public void onDestroy() {
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理

    }

    @OnClick({R.id.bt_newAddEnd, R.id.bt_newAddStart,R.id.bt_newAddWharf})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_newAddEnd:
                sateType = "3";
                startActivity(new Intent(getActivity(), SellectActivity.class));
                break;
            case R.id.bt_newAddWharf:
                sateType = "2";
                startActivity(new Intent(getActivity(), SellectActivity.class));
                break;
            case R.id.bt_newAddStart:
                sateType = "1";
                startActivity(new Intent(getActivity(), SellectActivity.class));
                break;
        }
    }


    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            if (location == null || mMapView == null) {
                return;
            }
            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f

            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

            int errorCode = location.getLocType();
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(radius)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(latitude)
                    .longitude(longitude).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLocation) {
                showCurrentLocation(mBaiduMap, location, true);
                isFirstLocation = false;
            }

            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            LogUtils.d("定位失败" + errorCode + "longitude" + longitude);

            boolean isWork = SPUtils.getInstance().getBoolean(Constants.IS_ON_WORK);
            //                定时上传车辆信息
            UploadMapEntity uploadMapEntity = new UploadMapEntity(carNo, Double.toString(latitude), Double.toString(longitude), "");
            if (isWork) {
                mPresenter.mapUpLoad(uploadMapEntity);
            }


        }
    }

    /*开启定位*/
    private void location() {

        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean providerEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (providerEnabled) {
            //声明mLocationOption对象
            initBaiDuLocation();
            mLocationClient.start();

        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("");
            builder.setMessage("为了您能正常使用请打开GPS");
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent intent = new Intent(
                            Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
                }
            });
            builder.setPositiveButton("", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create().show();
        }

    }

    private void initBaiDuLocation() {


        mLocationClient = new LocationClient(getActivity());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
        //可选，设置返回经纬度坐标类型，默认GCJ02
        //GCJ02：国测局坐标；
        //BD09ll：百度经纬度坐标；
        //BD09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标

        option.setScanSpan(30000);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
        //可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5 * 60 * 1000);
        //可选，V7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位

        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        option.setNeedNewVersionRgc(true);
        //可选，设置是否需要最新版本的地址信息。默认需要，即参数为true

        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
    }

    /*
        移动到某个定位点
     */
    private void mapMoveCenter(LatLng arg0) {

        LatLng ll = new LatLng(arg0.latitude, arg0.longitude);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }



}
