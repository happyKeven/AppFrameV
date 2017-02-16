package com.appframe.data.responseEntity;

import com.keven.library.base.BaseResponse;

import java.io.Serializable;


public class MainResp extends BaseResponse {
    private MainBean data;

    @Override
    public Object getData() {
        return data;
    }

    public class MainBean implements Serializable {
        public String version;
        public String url;
        public String versionInfo;

        public MainBean(String version, String url, String versionInfo) {
            this.version = version;
            this.url = url;
            this.versionInfo = versionInfo;
        }

        @Override
        public String toString() {
            return "VersionInfoBean{" +
                    "version='" + version + '\'' +
                    ", url='" + url + '\'' +
                    ", versionInfo='" + versionInfo + '\'' +
                    '}';
        }
    }
}
