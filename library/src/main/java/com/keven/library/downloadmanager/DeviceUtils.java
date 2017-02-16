package com.keven.library.downloadmanager;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;


public class DeviceUtils {

    /**
    * 获取应用名
    * @author keven
    * created at 2016/12/5 15:15
    */
    public static String getVersionName(Context context){
        String versionName = null;
        try {
            // 获取包管理者
            PackageManager pm = context.getPackageManager();
            // 获取packageInfo
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            // 获取versionName
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }


    /**
    * 获取应用版本
    * @author keven
    * created at 2016/12/5 15:16
    */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取包管理者
            PackageManager pm = context.getPackageManager();
            // 获取packageInfo
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            // 获取versionCode
            versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
}
