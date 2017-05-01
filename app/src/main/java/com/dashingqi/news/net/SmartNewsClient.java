package com.dashingqi.news.net;

import com.dashingqi.news.BuildConfig;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhangqi on 2017/4/21.
 */

public class SmartNewsClient {

    private static SmartNewsClient smartNewsClient;

    private OkHttpClient.Builder mBuilder;

    private SmartNewsClient() {
        initSetting();
    }

    public static SmartNewsClient getInstance() {
        if (smartNewsClient == null) {
            synchronized (SmartNewsClient.class) {
                if (smartNewsClient == null) {
                    smartNewsClient = new SmartNewsClient();
                }
            }
        }
        return smartNewsClient;
    }
    /**
     * 创建相应的服务接口
     */
    public <T> T create(Class<T> service, String baseUrl) {
        checkNotNull(service, "service is null");
        checkNotNull(baseUrl, "baseUrl is null");

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//为retrofit添加rxjava的支持 这时 observable 替代 call
                .build()
                .create(service);
    }

    private <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    private void initSetting() {

        //初始化OkHttp
        mBuilder = new OkHttpClient.Builder()
                .connectTimeout(9, TimeUnit.SECONDS)    //设置连接超时 9s
                .readTimeout(10, TimeUnit.SECONDS);      //设置读取超时 10s

        if (BuildConfig.DEBUG) { // 判断是否为debug
            // 如果为 debug 模式，则添加日志拦截器
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            mBuilder.addInterceptor(interceptor);
        }
    }
}
