package com.keven.library.data.bean;


/**
*<p>Title:DeviceInfo</p>
*<p>Description:设备信息
*<p>Copyright:Copyright(c)2016</p>
*@author keven
*created at 2016/12/15 16:18
*@version
*/
public class DeviceInfo {
    public String imei;       // 取出IMEI
    public String tel;        // 取出MSISDN，很可能为空
    public String iccid;      // 取出ICCID
    public String imsi;       // 取出IMSI

    public DeviceInfo(String imei, String tel, String iccid, String imsi) {
        this.imei = imei;
        this.tel = tel;
        this.iccid = iccid;
        this.imsi = imsi;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "imei='" + imei + '\'' +
                ", tel='" + tel + '\'' +
                ", iccid='" + iccid + '\'' +
                ", imsi='" + imsi + '\'' +
                '}';
    }
}
