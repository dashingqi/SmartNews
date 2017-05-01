package com.dashingqi.news.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by zhangqi on 2017/4/20.
 */

public abstract class BaseFragment extends Fragment {

    public boolean isViewInitiated;//view 加载好了
    public boolean isVisibleToUser;//
    public boolean isDataInitiated;//数据

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated=true;
        prepareFetchData();
    }

    /**
     * viewpager 来加载多个Fragment 会出现卡顿 加载慢 重写此方法 来解决此问题
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isViewInitiated=isVisibleToUser;
        prepareFetchData();
    }

    /**
     * 取数据操作 每个 页面的数据都是不同的 由子类去实现
     */
    public abstract void fetchData();

    public boolean prepareFetchData(){
        return prepareFetchData(false);
    }
    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }
}
