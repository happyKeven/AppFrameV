package com.keven.library.downloadmanager.manager.config;

import android.content.Context;
import android.os.Environment;

import com.keven.library.R;

import java.io.File;
import java.io.Serializable;

/**
 * <p>Title:downloadConfig</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright(c)2016</p>
 * User: 刘开峰
 * Date: 2016-12-05
 * Time: 16:06
 */
public class UploadConfig implements Serializable{
    // 目标文件存储的文件夹路径
    private String destFileDir = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "M_DEFAULT_DIR";
    // 目标文件存储的文件名
    private String destFileName = "officalcarsystem.apk";
    private int notificationSmallIcon = R.mipmap.ic_launcher;
    private String notificationContentTitle = "华清泊车更新";

    public static class Builder {
        private final UploadConfig downloadConfig = new UploadConfig();

        public Builder(Context context) {
        }

        public Builder setDestFileDir(String destFileDir) {
            downloadConfig.destFileDir = destFileDir;
            return this;
        }

        public Builder setDestFileName(String destFileName) {
            downloadConfig.destFileName = destFileName;
            return this;
        }


        public Builder setNotificationSmallIcon(int notificationSmallIcon) {
            downloadConfig.notificationSmallIcon = notificationSmallIcon;
            return this;
        }


        public Builder setNotificationContentTitle(String notificationContentTitle) {
            downloadConfig.notificationContentTitle = notificationContentTitle;
            return this;
        }

        public UploadConfig config() {
            return downloadConfig;
        }
    }

    public String getDestFileDir() {
        return destFileDir;
    }

    public String getDestFileName() {
        return destFileName;
    }

    public int getNotificationSmallIcon() {
        return notificationSmallIcon;
    }

    public String getNotificationContentTitle() {
        return notificationContentTitle;
    }
}
