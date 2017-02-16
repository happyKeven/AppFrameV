package com.keven.library.downloadmanager.manager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.keven.library.downloadmanager.DeviceUtils;
import com.keven.library.downloadmanager.DownLoadService;
import com.keven.library.downloadmanager.manager.bean.VersionInfo;
import com.keven.library.downloadmanager.manager.config.UploadConfig;


public class UpdateManager {
    public static final String UPLOAD_CONFIG_KEY = "upload_config_key";
    public static final String VERSION_INFO_KEY = "version_info_key";


    private Context mContext;

    public UpdateManager(Context context) {
        this.mContext = context;
    }


    /**
    * 在这里请求后台接口，获取更新的内容和最新的版本号
    * @author keven
    * created at 2016/12/5 15:30
    */
    public void checkUpdate(final boolean isToast, UploadConfig uploadConfig, VersionInfo versionInfo) {
        int mVersion_code = DeviceUtils.getVersionCode(mContext);// 当前的版本号
        if (mVersion_code < Integer.parseInt(versionInfo.getVersionCode())) {
            // 显示提示对话
            showNoticeDialog(uploadConfig, versionInfo);
        } else {
            if (isToast) {
                Toast.makeText(mContext, "已经是最新版本", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
    * 显示更新对话框
    * @author keven
    * created at 2016/12/5 15:31
    */
    public void showNoticeDialog(final UploadConfig uploadConfig, final VersionInfo versionInfo) {
        // 构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("更新提示");
        builder.setMessage(versionInfo.getUpdateContent());
        // 更新
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(mContext, DownLoadService.class);
                intent.putExtra(UPLOAD_CONFIG_KEY, uploadConfig);
                intent.putExtra(VERSION_INFO_KEY, versionInfo);
                mContext.startService(intent);
            }
        });
        // 稍后更新
        builder.setNegativeButton("以后更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog noticeDialog = builder.create();
        noticeDialog.show();
    }
}
