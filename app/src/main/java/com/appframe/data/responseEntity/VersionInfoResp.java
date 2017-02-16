package com.appframe.data.responseEntity;

import com.keven.library.base.BaseResponse;
import com.keven.library.downloadmanager.manager.bean.VersionInfo;


public class VersionInfoResp extends BaseResponse {
    private VersionInfo data;

    @Override
    public Object getData() {
        return data;
    }

    public class VersionInfoBean {
    }
}
