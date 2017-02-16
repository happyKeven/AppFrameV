package com.keven.library.downloadmanager.manager.bean;

import java.io.Serializable;

/**
 * <p>Title:VersionInfo</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright(c)2016</p>
 * User: 刘开峰
 * Date: 2017-01-23
 * Time: 14:49
 */
public class VersionInfo implements Serializable{

    private String updateContent;
    private String versionCode;
    private String downloadUrl;

    public VersionInfo() {
    }

    public VersionInfo(String updateContent, String versionCode, String downloadUrl) {
        this.updateContent = updateContent;
        this.versionCode = versionCode;
        this.downloadUrl = downloadUrl;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }
}
