package com.dashingqi.news.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * 状态存储工具类
 * Created by zhangqi on 2017/4/12.
 */

public class SPUtils {
    public static String getString(Context ctx,String key,String defaultVlue){
        SharedPreferences prefs= ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        return prefs.getString(key,defaultVlue);
    }
    public static void setString(Context ctx,String key,String value) {
        SharedPreferences prefs = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public static boolean getBoolean(Context ctx,String key,boolean defaultVlue){
        SharedPreferences prefs= ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        return prefs.getBoolean(key,defaultVlue);
    }
    public static void setBoolean(Context ctx,String key,boolean value) {
        SharedPreferences prefs = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }
}
