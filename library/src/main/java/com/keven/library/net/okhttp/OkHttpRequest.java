package com.keven.library.net.okhttp;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.keven.library.BaseApplication;
import com.keven.library.utils.NetworkUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.GenericsCallback;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.cookie.CookieJarImpl;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.CookieJar;
import okhttp3.MediaType;

import static com.zhy.http.okhttp.OkHttpUtils.post;

/**
 * <p>Title:OkHttpUtils</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright(c)2016</p>
 * User: 刘开峰
 * Date: 2017-02-08
 * Time: 12:28
 */
public class OkHttpRequest {

    public static void postJsonRequest(String url, String content, GenericsCallback genericsCallback) {
        if (!NetworkUtil.isNetworkConnected(BaseApplication.getContext())) {
            Toast.makeText(BaseApplication.getContext(), "请连接网络或稍后重试...", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(content)) {
            content = "{}";
        }

        OkHttpUtils
                .postString()
                .url(url)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(content) // json格式
                .build()
                .execute(null == genericsCallback?new DefaultCallBack():genericsCallback);

    }

    public static void getHtml(String url, StringCallback stringCallback) {
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(stringCallback);
    }


    public static void postString(String url, String jsonContent, StringCallback stringCallback) {
        OkHttpUtils
                .postString()
                .url(url)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(jsonContent)
                .build()
                .execute(stringCallback);

    }

    public static void postFile(Context context, String url, StringCallback stringCallback) {
        File file = new File(Environment.getExternalStorageDirectory(), "messenger_01.png");
        if (!file.exists()) {
            Toast.makeText(context, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }
        OkHttpUtils
                .postFile()
                .url(url)
                .file(file)
                .build()
                .execute(stringCallback);


    }

    /**
    public void getUser(String url, HashMap<String, String> map, GenericsCallback<T> genericsCallback) {
        PostFormBuilder postFormBuilder = OkHttpUtils
                .post() //
                .url(url); //
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        Iterator iterator = entrySet.iterator();
        while(iterator.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>)iterator.next();
            String key = entry.getKey();
            String value = entry.getValue();
            postFormBuilder.addParams(key, value);
        }
        postFormBuilder.build() //
            .execute(genericsCallback);
    }


    public void getUsers(View view)
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", "zhy");
        String url = mBaseUrl + "user!getUsers";
        //
                post()//
                .url(url)//
                //                .params(params)//
                .build()//
                .execute(new ListUserCallback()//
                {
                    @Override
                    public void onError(Call call, Exception e, int id)
                    {
                        mTv.setText("onError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(List<User> response, int id)
                    {
                        mTv.setText("onResponse:" + response);
                    }
                });
    } **/


    public static void getHttpsHtml(String url, StringCallback stringCallBack) {
        OkHttpUtils
                .get() //
                .url(url) //
                .id(101)
                .build() //
                .execute(stringCallBack);
    }

    public static void getImage(Context context, String url, BitmapCallback bitmapCallback) {
        OkHttpUtils
                .get() //
                .url(url) //
                .tag(context) //
                .build() //
                .connTimeOut(20000)
                .readTimeOut(20000)
                .writeTimeOut(20000)
                .execute(bitmapCallback);
    }

    /**
    * 单文件上传
    * @author keven
    * created at 2017/2/8 14:06
    */
    public static void uploadFile(Context context, UploadFileBean uploadFileBean, StringCallback stringCallBack) {
        File file = new File(Environment.getExternalStorageDirectory(), uploadFileBean.getFileName());
        if (!file.exists()) {
            Toast.makeText(context, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
            return;
        }

        /**
        Map<String, String> params = new HashMap<>();
        params.put("username", "张鸿洋");
        params.put("password", "123");

        Map<String, String> headers = new HashMap<>();
        headers.put("APP-Key", "APP-Secret222");
        headers.put("APP-Secret", "APP-Secret111");
         */

        post() //
                .addFile(uploadFileBean.getName(), uploadFileBean.getFileName(), file)//
                .url(uploadFileBean.getUrl()) //
                .params(uploadFileBean.getParams()) //
                .headers(uploadFileBean.getHeaders()) //
                .build() //
                .execute(stringCallBack);
    }

    /**
    * 多文件上传
    * @author keven
    * created at 2017/2/8 14:07
    */
    public static void multiFileUpload(Context context, List<UploadFileBean> uploadFileList, StringCallback stringCallBack) {
        File file = null;
        int fileCount = uploadFileList.size();
        boolean isAllFileValid = true;

        for (int i = fileCount - 1; i >= 0; i--) {
            file = new File(Environment.getExternalStorageDirectory(), uploadFileList.get(i).getFileName());
            if (!file.exists()) {
                Toast.makeText(context, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
                isAllFileValid = false;
                break;
            }
        }

        // 只要其中有一个文件不存在，就返回
        if (!isAllFileValid) {
            return;
        }


        String url = uploadFileList.get(0).getUrl();
        HashMap<String, String> params = uploadFileList.get(0).getParams();

        PostFormBuilder postFileBuilder = post();
        for (int i = fileCount - 1; i >= 0; i--) {
            UploadFileBean uploadFileBean = uploadFileList.get(i);
            postFileBuilder.addFile(uploadFileBean.getName(), uploadFileBean.getFileName(), file); //
        }

        postFileBuilder.url(url)
                .params(params) //
                .build() //
                .execute(stringCallBack);
    }

    /**
    * 下载文件
    * @author keven
    * created at 2017/2/8 14:30
    */
    public static void downloadFile(String url, FileCallBack fileCallBack) {
        OkHttpUtils //
                .get() //
                .url(url) //
                .build() //
                .execute(fileCallBack);
    }


    public static void otherRequestDemo(View view) {
        //also can use delete ,head , patch
        /*
        OkHttpUtils
                .put()//
                .url("http://11111.com")
                .requestBody
                        ("may be something")//
                .build()//
                .execute(new MyStringCallback());



        OkHttpUtils
                .head()//
                .url(url)
                .addParams("name", "zhy")
                .build()
                .execute();

       */
    }

    public static void clearSession() {
        CookieJar cookieJar = OkHttpUtils.getInstance().getOkHttpClient().cookieJar();
        if (cookieJar instanceof CookieJarImpl) {
            ((CookieJarImpl) cookieJar).getCookieStore().removeAll();
        }
    }

    final static class DefaultCallBack extends StringCallback {
        @Override
        public void onError(Call call, Exception e, int i) {
        }

        @Override
        public void onResponse(String s, int i) {
        }
    }
}
