package com.keven.library.downloadmanager;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.keven.library.downloadmanager.manager.UpdateManager;
import com.keven.library.downloadmanager.manager.bean.VersionInfo;
import com.keven.library.downloadmanager.manager.config.UploadConfig;
import com.keven.library.downloadmanager.manager.fileload.FileCallback;
import com.keven.library.downloadmanager.manager.fileload.FileResponseBody;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Url;


public class DownLoadService extends Service {
    private Context mContext;
    private int preProgress = 0;
    private int NOTIFY_ID = 1000;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private Retrofit.Builder retrofit;
    private UploadConfig uploadConfig = new UploadConfig();
    private VersionInfo versionInfo = new VersionInfo();


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mContext = this;
        if (null != intent) {
            uploadConfig = (UploadConfig) intent.getSerializableExtra(UpdateManager.UPLOAD_CONFIG_KEY);
            versionInfo = (VersionInfo) intent.getSerializableExtra(UpdateManager.VERSION_INFO_KEY);
        }

        loadFile();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 下载文件
     * @author keven
     * created at 2016/12/5 15:22
     * 参考链接:
     * 1 http://square.github.io/retrofit/
     * 2 http://blog.csdn.net/bitian123/article/details/51899716
     */

    private void loadFile() {

        initNotification();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder();
        }

        String downloadUrl = versionInfo.getDownloadUrl();
        int lastSlash = downloadUrl.lastIndexOf("/");
        String baseUrl = downloadUrl.substring(0, lastSlash + 1);
        String suffixUrl = downloadUrl.substring(lastSlash + 1);

        retrofit.baseUrl(baseUrl)
                .client(initOkHttpClient())
                .build()
                .create(IFileLoad.class)
                .loadFile(suffixUrl)
                .enqueue(new FileCallback(uploadConfig.getDestFileDir(), uploadConfig.getDestFileName()) {
                    @Override
                    public void onSuccess(File file) {
                        Log.e("zs", "请求成功");
                        // 安装软件
                        cancelNotification();
                        installApk(file);
                    }

                    @Override
                    public void onLoading(long progress, long total) {
                        Log.e("zs", progress + "----" + total);
                        updateNotification(progress * 100 / total);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("zs", "请求失败");
                        cancelNotification();
                    }
                });
    }

    public interface IFileLoad {
        @GET
        Call<ResponseBody> loadFile(@Url String fileUrl);
    }

    /**
    * 安装软件
    * @author keven
    * created at 2016/12/5 15:28
    */
    private void installApk(File file) {
        Uri uri = Uri.fromFile(file);
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        install.setDataAndType(uri, "application/vnd.android.package-archive");
        // 执行意图进行安装
        mContext.startActivity(install);
    }

    /**
    * 初始化OkHttpClient
    * @author keven
    * created at 2016/12/5 15:28
    */
    private OkHttpClient initOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(100000, TimeUnit.SECONDS);
        builder.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse
                        .newBuilder()
                        .body(new FileResponseBody(originalResponse))
                        .build();
            }
        });
        return builder.build();
    }

    /**
    * 初始化Notification通知
    * @author keven
    * created at 2016/12/5 15:29
    */
    public void initNotification() {
        builder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(uploadConfig.getNotificationSmallIcon())
                .setContentText("0%")
                .setContentTitle(uploadConfig.getNotificationContentTitle())
                .setProgress(100, 0, false);
        notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID, builder.build());
    }

    /**
    * 更新通知
    * @author keven
    * created at 2016/12/5 15:29
    */
    public void updateNotification(long progress) {
        int currProgress = (int) progress;
        if (preProgress < currProgress) {
            builder.setContentText(progress + "%");
            builder.setProgress(100, (int) progress, false);
            notificationManager.notify(NOTIFY_ID, builder.build());
        }
        preProgress = (int) progress;
    }

    /**
    * 取消通知
    * @author keven
    * created at 2016/12/5 15:29
    */
    public void cancelNotification() {
        notificationManager.cancel(NOTIFY_ID);
    }
}
