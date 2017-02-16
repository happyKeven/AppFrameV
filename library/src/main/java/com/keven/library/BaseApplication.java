package com.keven.library;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.IoniconsModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.MeteoconsModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.joanzapata.iconify.fonts.TypiconsModule;
import com.joanzapata.iconify.fonts.WeathericonsModule;

import java.io.File;


/**
 * Created by Anthony on 2016/6/3.
 * Class Note:
 * Base Application for Application
 * use in AndroidManifest.xml
 */
public class BaseApplication extends Application {

    private static Handler mHandler;
    private static Context mContext;



    @Override
    public void onCreate() {

        super.onCreate();

        mHandler = new Handler();
        mContext = getApplicationContext();


        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new TypiconsModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new MeteoconsModule())
                .with(new WeathericonsModule())
                .with(new SimpleLineIconsModule())
                .with(new IoniconsModule());
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static Context getContext() {
        return mContext;
    }

    public static Handler getHanlder() {
        return mHandler;
    }



    public static BaseApplication get(Context context) {
        return (BaseApplication) context.getApplicationContext();
    }

    /**
     * get cache dir
     *
     * @return cache directory
     */
    @Override
    public File getCacheDir() {
        Log.i("getCacheDir", "cache sdcard state: " + Environment.getExternalStorageState());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File cacheDir = getExternalCacheDir();
            if (cacheDir != null && (cacheDir.exists() || cacheDir.mkdirs())) {
                Log.i("getCacheDir", "cache dir: " + cacheDir.getAbsolutePath());
                return cacheDir;
            }
        }

        File cacheDir = super.getCacheDir();
        Log.i("getCacheDir", "cache dir: " + cacheDir.getAbsolutePath());

        return cacheDir;
    }
}
