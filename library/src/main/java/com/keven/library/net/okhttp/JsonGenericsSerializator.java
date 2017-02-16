package com.keven.library.net.okhttp;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.callback.IGenericsSerializator;


public class JsonGenericsSerializator implements IGenericsSerializator {
    @Override
    public <T> T transform(String response, Class<T> classOfT) {
        return JSON.parseObject(response, classOfT);
    }

}
