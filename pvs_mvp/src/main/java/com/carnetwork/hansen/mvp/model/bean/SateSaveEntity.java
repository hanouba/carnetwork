package com.carnetwork.hansen.mvp.model.bean;

/**
 * @author HanN on 2020/10/30 9:51
 * @email: 1356548475@qq.com
 * @project carnetwork
 * @description: 提交起点终点
 * @updateuser:
 * @updatedata: 2020/10/30 9:51
 * @updateremark:
 * @version: 2.1.67
 */
public class SateSaveEntity {
//    	"lat": "39.989051000000000",
//                "lon": "114.394495000000000",
//                "sateName": "重合",
//                "sateType":  1起点磅房 2终点码头 3终点公司

    private String lat;
    private String lon;
    private String sateName;
    private String sateType;

    public SateSaveEntity(String lat, String lon, String sateName, String sateType) {
        this.lat = lat;
        this.lon = lon;
        this.sateName = sateName;
        this.sateType = sateType;
    }
}
