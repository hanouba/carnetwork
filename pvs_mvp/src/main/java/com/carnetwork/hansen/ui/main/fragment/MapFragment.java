package com.carnetwork.hansen.ui.main.fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Text;
import com.blankj.utilcode.util.ToastUtils;
import com.carnetwork.hansen.R;
import com.carnetwork.hansen.base.BaseFragment;
import com.carnetwork.hansen.mvp.contract.main.MapContract;
import com.carnetwork.hansen.mvp.presenter.main.MapPresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
    AMap aMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        initview(savedInstanceState, view);

        return view;

    }

    private void initview(Bundle savedInstanceState, View view) {
        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        //隐藏缩放控件
        aMap.getUiSettings().setZoomControlsEnabled(false);
    }



    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_map;
    }
    View infoWindow = null;
    @Override
    protected void initEventAndData() {

        showCurrentLocation();

        getData();

        aMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                if(infoWindow == null) {
                    infoWindow = LayoutInflater.from(getContext()).inflate(
                            R.layout.custom_info_window, null);
                }
                render(marker, infoWindow);
                return infoWindow;
            }
        });
    }
    /**
     * 自定义infowinfow窗口
     */
    public void render(Marker marker, View view) {
        TextView tvCarNo = view.findViewById(R.id.tv_map_car_no);
        TextView tvCarL = view.findViewById(R.id.tv_car_map_licence);
        ImageView ivCarPhone = view.findViewById(R.id.iv_car_phone);
        tvCarNo.setText(marker.getTitle());
        String snippet = marker.getSnippet();
        String[] substring = snippet.split(",");
        tvCarL.setText(substring[0]);



        ivCarPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showLong("打电话"+substring[1]);
            }
        });


    }
    private List<Map<String, String>> list;
    private void getData() {
        //模拟数据源
         list = getLatData();

        //循坏在地图上添加自定义marker
        for (int i = 0; i < list.size(); i++) {
            LatLng latLng = new LatLng(Double.parseDouble(list.get(i).get("latitude")), Double.parseDouble(list.get(i).get("longitude")));
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(latLng);
            markerOption.title(list.get(i).get("title"));
            markerOption.snippet(list.get(i).get("carLince"));
            //自定义点标记的icon图标为drawable文件下图片
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_car_blue)));
            markerOption.draggable(false);
            aMap.addMarker(markerOption);

        }


    }

    private void showCurrentLocation() {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.bt_reset)
    public void onViewClicked() {
        showCurrentLocation();
    }

    /**
     * 模拟数据源
     */
    private List<Map<String, String>> getLatData() {
        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("title", "车辆编号" + i);
            map.put("carLince", "车牌15848766" + (i-1)+","+"1379578546"+i);
            map.put("latitude", 30 + Math.random() + "");
            map.put("longitude", 114 + Math.random() + "");
            list.add(map);
        }
        return list;
    }
}
