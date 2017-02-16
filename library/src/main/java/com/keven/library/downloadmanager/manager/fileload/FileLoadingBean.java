package com.keven.library.downloadmanager.manager.fileload;

public class FileLoadingBean {

    long total; // 文件大小
    long progress; // 已下载大小

    public long getProgress() {
        return progress;
    }

    public long getTotal() {
        return total;
    }

    public FileLoadingBean(long total, long progress) {
        this.total = total;
        this.progress = progress;
    }
}
