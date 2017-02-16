package com.keven.library.net.okhttp;

import java.util.HashMap;

/**
 * <p>Title:UploadFileBean</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright(c)2016</p>
 * User: 刘开峰
 * Date: 2017-02-08
 * Time: 13:54
 */
public class UploadFileBean {
    private String url;
    private String name;
    private String fileName;
    private HashMap<String, String> params;
    private HashMap<String, String> headers;

    public UploadFileBean(String url, String name, String fileName, HashMap<String, String> params, HashMap<String, String> headers) {
        this.url = url;
        this.name = name;
        this.fileName = fileName;
        this.params = params;
        this.headers = headers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }
}
