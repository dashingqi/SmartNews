package com.dashingqi.news.net;

import com.dashingqi.news.bean.NewsDataBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zhangqi on 2017/4/21.
 */

public interface SmartNewsService {

    /**
     * @param type 新闻类型
     * @return  查询结束 返回 数据的 被观察者
     */
    @GET("toutiao/index?key=37190031d70290e49b20ac452eb85fe9")
    Observable<NewsDataBean> getNewsData(@Query("type") String type);
}
