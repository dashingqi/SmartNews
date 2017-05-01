package com.dashingqi.news.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by zhangqi on 2017/4/21.
 */

public class HttpUtils {
    public static void sendOKHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
