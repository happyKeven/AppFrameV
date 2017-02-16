package com.keven.library.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;
import java.util.Locale;

/**
*<p>Title:ApplicationUtils</p>
*<p>Description:${}
*<p>Copyright:Copyright(c)2016</p>
*@author keven
*created at 2016/12/5 14:37
*@version
*/
public class ApplicationUtils {
    
    /**
    * 获取VersionName
    *@author keven
    *created at 2016/12/5 14:36
    */
    public static String getVersionName(Context context) {
        PackageManager pm = context.getPackageManager();
        String versionName = "";
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            versionName = "1.0";
        }
        return versionName;
    }

    /**
    * 获取VersionCode
    *@author keven
    *created at 2016/12/5 14:37
    */
    public static String getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        int versionCode = 0;

        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            versionCode = 0;
        }
        return String.valueOf(versionCode);
    }

    /**
     * 判断应用是否已经启动
     * @param context 一个context
     * @param packageName 要判断应用的包名
     * @return boolean
     */
    public static boolean isAppAlive(Context context, String packageName){
        ActivityManager activityManager =
                (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for(int i = 0; i < processInfos.size(); i++){
            if(processInfos.get(i).processName.equals(packageName)){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断某个服务是否正在运行的方法
     * @param mContext
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public static boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    public static String getSystemLanuage(Context context) {
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();
        String country = locale.getCountry();
        return language.concat(country);
    }

    /**
     * 当前语言环境是否为中文
     * @author keven
     * created at 2016/12/5 14:51
    */
    public static boolean languageIsZh(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh"))
            return true;
        else
            return false;
    }
}
