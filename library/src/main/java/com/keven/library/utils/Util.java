package com.keven.library.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Util {

	public static String clearSeparator(String paramString) {
		if (!TextUtils.isEmpty(paramString)) {
			String str = paramString.replace("-", "");
			paramString = str;
		}
		return paramString;
	}

	public static boolean checkString(String paramString) {
		return (paramString != null) && (!TextUtils.isEmpty(paramString));
	}

	public static boolean checkMoneyValid(String paramString) {
		if (checkString(paramString)) {
			return Pattern.compile("^((\\d+)|0|)(\\.(\\d+)$)?")
					.matcher(paramString).matches();
		}
		return false;
	}

	public static boolean checkMobile(String paramString) {
		boolean bool = false;
		try {
			if (paramString.length() == 11) {
				bool = Pattern.compile("^[1][0-9]{10}$").matcher(paramString)
						.matches();
			}
		} catch (PatternSyntaxException localPatternSyntaxException) {
		}
		return bool;
	}

	public static String toFenByYuan(String paramString) {
		String tmpStr = paramString.replaceAll(",", "");
		if ((checkString(tmpStr)) && (checkMoneyValid(tmpStr))) {
			try {
				String str = new BigDecimal((String) tmpStr).multiply(
						new BigDecimal("100")).setScale(0, 3)
						+ "";
				tmpStr = str;
			} catch (NumberFormatException localNumberFormatException) {
				localNumberFormatException.printStackTrace();
				tmpStr = 100 * Integer.parseInt((String) tmpStr) + "";
			}
		}
		return tmpStr;
	}

	public static String toYuanByFen(String paramString) {
		String tmpStr = paramString.replaceAll(",", "");
		if ((checkString(tmpStr)) && (checkMoneyValid(tmpStr))) {
			try {
				double d = new BigDecimal((String) tmpStr)
						.divide(new BigDecimal("100")).setScale(2, 3)
						.doubleValue();
				NumberFormat localNumberFormat = NumberFormat
						.getNumberInstance();
				localNumberFormat.setMinimumFractionDigits(2);
				String str = localNumberFormat.format(d);
				tmpStr = str;
			} catch (NumberFormatException localNumberFormatException) {
				localNumberFormatException.printStackTrace();
				tmpStr = Integer.parseInt((String) tmpStr) / 100 + "";
			}
		}
		return tmpStr;
	}
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss", Locale.getDefault());
	
	private static Random ra = new Random(System.currentTimeMillis());
	
	public static String createASerial() {
		long time = System.currentTimeMillis();
		int ramNum = ra.nextInt(1000)+1000;
		String ramStr = String.valueOf(ramNum).substring(1);
		return sdf.format(time)+ramStr;
	}
	
	private static final SimpleDateFormat sdf2 = new SimpleDateFormat(
			"yyyy-MM-dd", Locale.CHINA);
	
	/**
	 * @return
	 */
	public static String getCurrentDate() {
		String curDateStr = sdf2.format(new Date());
		return curDateStr;
	}

	private static int mStatusBarHeight;
	
	/**
	 * 获取状态栏高度
	 * 
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		java.lang.reflect.Field field = null;
		int x = 0;
		int statusBarHeight = mStatusBarHeight;
		try
		{
			if (statusBarHeight!=0) {
				return statusBarHeight;
			}
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
			mStatusBarHeight = statusBarHeight;
			return statusBarHeight;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return statusBarHeight;
	}
	
	public static boolean hasFroyo() {
        // Can use static final constants like FROYO, declared in later versions
        // of the OS since they are inlined at compile time. This is guaranteed behavior.
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }
    
    /**
     * 检验连接
     * @param context
     * @return
     */
    public static boolean checkConnection(Context context) {
    	boolean isConnected = true;
        final ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
        	isConnected = false;
        }
        return isConnected;
    }
    
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public static Point getDisplaySize(Context context) {
    	Point size = new Point();
    	Display display = ((WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    	if (Build.VERSION.SDK_INT < 13) {
    		size.x = display.getWidth();
    		size.y = display.getHeight();
    	} else {
    		display.getSize(size);
    	}
    	return size;
    }
	
    /**
	 * 获取应用版本号
	 * @param context
	 * @return
	 */
	public static int getAppVersionCode(Context context) {
		int versionCode = 0;

		PackageManager pm = context.getPackageManager();
		PackageInfo pi;
		try {
			pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionCode = pi.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		}

		return versionCode;
	}

	/**
	 * 获取应用版本名称
	 */
	public static String getAppVersionName(Context context) {
		String versionName = "";
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		}
		return versionName;
	}
    
}
