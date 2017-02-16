package com.keven.library.base;

import android.text.TextUtils;


public abstract class BaseResponse {
    private static final String DESSUCCESS = "success";
    private static final String DESFAIL = "fail";

    private int code;
    private String msg;

    public abstract Object getData();

    public boolean isSuccess() {
        if (!TextUtils.isEmpty(msg) && DESSUCCESS.equals(msg)) {
            return true;
        } else {
            return false;
        }
    }
}
