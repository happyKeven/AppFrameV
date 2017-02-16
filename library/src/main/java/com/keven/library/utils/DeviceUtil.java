package com.keven.library.utils;/**
 * Created by keven on 2016/11/1.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 获取设备信息（尺寸、设备信息）
 * Date: 2016-11-01
 * Time: 10:20
 */
public class DeviceUtil {

    /**
     * 获取设备屏幕大小
     * @author keven
     * create at 2016/11/1 10:25
     */
    public static Display getScreenSize(Activity context) {
        // 方法一: 通过WindowManager获取
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);

        Display display = new Display(dm.heightPixels * dm.density, dm.widthPixels * dm.density);
        System.out.println("heigth : " + dm.heightPixels * dm.density);
        System.out.println("width : " + dm.widthPixels * dm.density);
        System.out.println("density : " + dm.density);
        return display;
    }

    public static class Display {
        public float heightPixels;
        public float widthPixels;

        Display(float heightPixels, float widthPixels) {
            this.heightPixels = heightPixels;
            this.widthPixels = widthPixels;
        }
    }

    /**
     * 获取设备信息
     * @author keven
     * create at 2016/11/1 15:04
     */
    public static com.keven.library.data.bean.DeviceInfo getDeviceInfo(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();       //取出IMEI
        String tel = tm.getLine1Number();     //取出MSISDN，很可能为空
        String iccid =tm.getSimSerialNumber();  //取出ICCID
        String imsi =tm.getSubscriberId();     //取出IMSI

        com.keven.library.data.bean.DeviceInfo deviceInfo = new com.keven.library.data.bean.DeviceInfo(imei, tel, iccid, imsi);
        return deviceInfo;
    }

    @SuppressWarnings("deprecation")
    public static Point getDisplaySize(Context context) {
        Point size = new Point();
        android.view.Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2) {
            size.x = display.getWidth();
            size.y = display.getHeight();
        } else {
            display.getSize(size);
        }
        return size;
    }
}
