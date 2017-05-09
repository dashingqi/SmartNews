package com.dashingqi.news.activity;

import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.dashingqi.news.R;
import com.dashingqi.news.fragment.NewsFragment;
import com.dashingqi.news.fragment.PersonalFragment;
import com.dashingqi.news.fragment.PicFragment;
import com.dashingqi.news.fragment.TodayFragment;
import com.dashingqi.news.fragment.VideoFragment;
import com.dashingqi.news.view.NoScrollViewPager;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

   @BindView(R.id.fl_content)
   FrameLayout flContent;

    @BindView(R.id.bottom_bar)
    BottomBar bottomBar;

    private NewsFragment newsFragment; //新闻
    private PicFragment picFragment;//图片
    private VideoFragment videoFragment;//视频
    private TodayFragment todayFragment;//历史上的今天
    private PersonalFragment personalFragment;//个人中心

    private Fragment currentFragment;//当前Fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }


        //手机软键盘适应屏幕
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
      //  initFragment();

        //为底部按钮设置点击事件
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch(tabId){
                    case R.id.tab_news:
                        if (newsFragment==null) newsFragment= new NewsFragment();
                        switchFragment(newsFragment);
                        break;
                    case R.id.tab_pic:
                        if (picFragment==null) picFragment= new PicFragment();
                        switchFragment(picFragment);
                        break;
                    case R.id.tab_video:
                        if (videoFragment==null) videoFragment= new VideoFragment();
                        switchFragment(videoFragment);
                        break;
                    case R.id.tab_today:
                        if (todayFragment==null) todayFragment= new TodayFragment();
                        switchFragment(todayFragment);
                        break;
                    case R.id.tab_personal:
                        if (personalFragment==null) personalFragment= new PersonalFragment();
                        switchFragment(personalFragment);
                        break;
                }
            }
        });
    }

    /**
     * 选择替换当前的Fragment
     * @param state    要显示的Fragment
     */
    private void switchFragment(Fragment state) {
        //如果当前的currentFragment 就是 要显示的Fragment 就结束；
        if (currentFragment==state) return;
        //开启事务
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        //若果当前的Fragment 不为空 就隐藏当前的Fragment 不一样
        if (currentFragment!=null){
            transaction.hide(currentFragment);
        }
        //如果要实现的Fragment 已经添加进去了 就显示就可以了
        if (state.isAdded()){
            transaction.show(state);
        }else{
            //就将要显示的Fragment添加进去
            transaction.add(R.id.fl_content,state,state.getClass().getName());
           // transaction.replace(R.id.fl_content,state);
        }
        transaction.commit();
        //显示的Fragment 设置给当前的Fragment
        currentFragment=state;
    }
    /**
     * 初始化Fragment
     * 找到FragmentManager中存储的Fragment
     */
    private void initFragment() {
        FragmentManager fm = getSupportFragmentManager();
        newsFragment = (NewsFragment) fm.findFragmentByTag("NewsFragment");
        picFragment = (PicFragment) fm.findFragmentByTag("PicFragment");
        videoFragment = (VideoFragment) fm.findFragmentByTag("VideoFragment");
        todayFragment = (TodayFragment) fm.findFragmentByTag("TodayFragment");
        personalFragment = (PersonalFragment) fm.findFragmentByTag("PersonalFragment");
    }
}
