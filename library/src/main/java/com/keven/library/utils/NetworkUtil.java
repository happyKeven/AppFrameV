package com.keven.library.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {

    /**
     * Returns true if the Throwable is an instance of RetrofitError with an
     * http status code equals to the given one.
     */
    /*public static boolean isHttpStatusCode(Throwable throwable, int statusCode) {
        return throwable instanceof HttpException
                && ((HttpException) throwable).code() == statusCode;
    }*/

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null == cm) {
            return false;
        }

        NetworkInfo info = cm.getActiveNetworkInfo();
        if (null != info) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;

    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity, int requestCode) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction(Intent.ACTION_VIEW);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 检查当前网络是否可用
     * @return
     */
    public static boolean isNetworkAvailable(Activity activity) {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}