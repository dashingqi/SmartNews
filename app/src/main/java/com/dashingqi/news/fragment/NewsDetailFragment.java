package com.dashingqi.news.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.dashingqi.news.R;
import com.dashingqi.news.adapter.NewsDetailDataAdapter;
import com.dashingqi.news.bean.NewsDataBean;
import com.dashingqi.news.net.SmartNewsClient;
import com.dashingqi.news.net.SmartNewsService;
import com.dashingqi.news.util.ConstantValues;
import com.xiawei.webviewlib.WebViewActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangqi on 2017/4/21.
 */

@SuppressLint("ValidFragment") public class NewsDetailFragment extends Fragment {

    public String type;//新闻类型
    private List<NewsDataBean.ResultBean.DataBean> dataBeanList;


    @BindView(R.id.refresh_news_detail)
    SwipeRefreshLayout refresh;

    @BindView(R.id.rv_news_detail)
    RecyclerView rvNewsDetail;

    private NewsDetailDataAdapter mNewsDetailDataAdapter;//新闻详情页适配器

    public NewsDetailFragment(String type){
        this.type=type;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_detail, null);
        ButterKnife.bind(this,view);

        mNewsDetailDataAdapter = new NewsDetailDataAdapter();

//        mNewsDetailDataAdapter.setEnableLoadMore(true);

        mNewsDetailDataAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);//给加载的列表添加动画

        //******************设置下拉刷新************
        refresh.setColorSchemeColors(Color.BLUE,Color.BLUE);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateData();
            }
        });
        //##################### RecyclerView 填充数据 ###########
        rvNewsDetail.setAdapter(mNewsDetailDataAdapter);
        //设置RecyclerView的布局
        rvNewsDetail.setLayoutManager(new LinearLayoutManager(getActivity()));

        rvNewsDetail.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //加载新闻内容 webview加载
                WebViewActivity.startUrl(getActivity(),
                        ((NewsDataBean.ResultBean.DataBean)adapter
                                .getItem(position)).getUrl());
            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
            }
        });

        return view;
    }

    //TODO 没有执行父类的该方法 重写子类的  调用加载数据
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateData();
    }

//    @Override
//    public void fetchData() {
//        updateData();
//      //  System.out.println("fetchData方法执行了");
//
//    }
    /**
     * 更新数据 取数据
     */
    public void updateData() {

        refresh.setRefreshing(true);

         SmartNewsClient
                 .getInstance()
                 .create(SmartNewsService.class, ConstantValues.JUHE_URL)
                 .getNewsData(type)
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(new Consumer<NewsDataBean>() {
                    @Override
                    public void accept(NewsDataBean newsDataBean) throws Exception {
                        dataBeanList=newsDataBean.getResult().getData();
                        mNewsDetailDataAdapter.setNewData(dataBeanList);
//                        mNewsDetailDataAdapter.notifyDataSetChanged();
                        refresh.setRefreshing(false);
                     }
                 }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        refresh.setRefreshing(false);
                    }
        });
    }

}
