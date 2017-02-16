package com.appframe.application;

import android.os.StrictMode;

import com.etisk.officalcar.BuildConfig;
import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.keven.library.BaseApplication;
import com.keven.library.database.DatabaseHelper;
import com.keven.library.utils.CrashHandler;
import com.keven.library.utils.SubTaskHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.MemoryCookieStore;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

/**
*<p>Title:MainApplication</p>
*<p>Description:${}
*<p>Copyright:Copyright(c)2016</p>
*@author keven
*created at 2016/12/18 23:32
*@version 
*/
public class MainApplication extends BaseApplication {
    public static MainApplication mApplication;
    private DatabaseHelper mDbHelper;
    
    private String CER_12306 = "-----BEGIN CERTIFICATE-----\n" +
            "MIICmjCCAgOgAwIBAgIIbyZr5/jKH6QwDQYJKoZIhvcNAQEFBQAwRzELMAkGA1UEBhMCQ04xKTAn\n" +
            "BgNVBAoTIFNpbm9yYWlsIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MQ0wCwYDVQQDEwRTUkNBMB4X\n" +
            "DTA5MDUyNTA2NTYwMFoXDTI5MDUyMDA2NTYwMFowRzELMAkGA1UEBhMCQ04xKTAnBgNVBAoTIFNp\n" +
            "bm9yYWlsIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MQ0wCwYDVQQDEwRTUkNBMIGfMA0GCSqGSIb3\n" +
            "DQEBAQUAA4GNADCBiQKBgQDMpbNeb34p0GvLkZ6t72/OOba4mX2K/eZRWFfnuk8e5jKDH+9BgCb2\n" +
            "9bSotqPqTbxXWPxIOz8EjyUO3bfR5pQ8ovNTOlks2rS5BdMhoi4sUjCKi5ELiqtyww/XgY5iFqv6\n" +
            "D4Pw9QvOUcdRVSbPWo1DwMmH75It6pk/rARIFHEjWwIDAQABo4GOMIGLMB8GA1UdIwQYMBaAFHle\n" +
            "tne34lKDQ+3HUYhMY4UsAENYMAwGA1UdEwQFMAMBAf8wLgYDVR0fBCcwJTAjoCGgH4YdaHR0cDov\n" +
            "LzE5Mi4xNjguOS4xNDkvY3JsMS5jcmwwCwYDVR0PBAQDAgH+MB0GA1UdDgQWBBR5XrZ3t+JSg0Pt\n" +
            "x1GITGOFLABDWDANBgkqhkiG9w0BAQUFAAOBgQDGrAm2U/of1LbOnG2bnnQtgcVaBXiVJF8LKPaV\n" +
            "23XQ96HU8xfgSZMJS6U00WHAI7zp0q208RSUft9wDq9ee///VOhzR6Tebg9QfyPSohkBrhXQenvQ\n" +
            "og555S+C3eJAAVeNCTeMS3N/M5hzBRJAoffn3qoYdAO1Q8bTguOi+2849A==\n" +
            "-----END CERTIFICATE-----";


    @Override
    public void onCreate() {
        super.onCreate();

        // anrMonitor();

        // carltonMonitor();

        // okHttpInitConfig();

        // loggerConfig();

        // initDatabaseHelper();

        initCrashHandler();


    }

    private void loggerConfig() {
        /*
        Logger
                .init(YOUR_TAG)                 // default PRETTYLOGGER or use just init()
                .methodCount(3)                 // default 2
                .hideThreadInfo()               // default shown
                .logLevel(LogLevel.NONE)        // default LogLevel.FULL
                .methodOffset(2)                // default 0
                .logAdapter(new AndroidLogAdapter()); //default AndroidLogAdapter
        */
    }

    /**
    * 用来监控卡顿问题
    * @author keven
    * created at 2017/2/9 11:28
    */
    private void carltonMonitor() {
        // 在主线程初始化调用 (主要用来监控应用主线程的卡顿)
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
    }


    /**
    * 用来监控ANR问题
    * @author keven
    * created at 2017/2/9 11:27
    */
    private void anrMonitor() {
        if (BuildConfig.DEBUG) {
            /**
             * 解决这些检测到的问题能够减少应用发生ANR的概率
             */
            // 开启线程模式
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());

            // 开启虚拟机模式
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
        }
    }

    /**
    * okhttp初始化配置
    * @author keven
    * created at 2017/2/9 11:22
    */
    private void okHttpInitConfig() {
        // ClearableCookieJair cookieJar1 = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getApplicationContext()));
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        CookieJarImpl cookieJar1 = new CookieJarImpl(new MemoryCookieStore());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("TAG"))
                .cookieJar(cookieJar1)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession sessiont)
                    {
                        return true;
                    }
                })
                // .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    /**
    * 崩溃接管程序初始化
    * @author keven
    * created at 2017/2/9 11:15
    */
    private void initCrashHandler() {
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }

    /**
    * 预先初始化下DatabaseHelper
    * @author keven
    * created at 2017/2/9 11:37
    */
    private void initDatabaseHelper() {
        SubTaskHelper.getInstance().executeBackground(new Runnable() {
            @Override
            public void run() {
                getDatabaseHelper();
            }
        });
    }

    public class AppBlockCanaryContext extends BlockCanaryContext {
        // 实现各种上下文，包括应用标识符、用户uid、网络类型、卡慢判断阈值、Log保存位置等
    }


    public DatabaseHelper getDatabaseHelper() {
        if (mDbHelper == null) {
            mDbHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return mDbHelper;
    }
}
