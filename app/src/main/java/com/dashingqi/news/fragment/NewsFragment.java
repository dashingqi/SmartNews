package com.dashingqi.news.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dashingqi.news.R;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangqi on 2017/4/20.
 */

public class NewsFragment extends Fragment {
    private String [] type;//指示器数据
    private String [] typeEn;
    @BindView(R.id.news_view_pager)
    ViewPager newsViewPager;
    private NewsPagerAdapter mNewsPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragemnt_news,null);
        ButterKnife.bind(this,view);
        type = getResources().getStringArray(R.array.news_type_cn);
        typeEn = getResources().getStringArray(R.array.news_type_en);

        //ViewPager填充数据
        mNewsPagerAdapter = new NewsPagerAdapter(getActivity().getSupportFragmentManager());
        newsViewPager.setAdapter(mNewsPagerAdapter);

        //*********************给指示器填充数据****************************** github 上的做法
        MagicIndicator magicIndicator = (MagicIndicator)view. findViewById(R.id.magic_indicator);
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return type == null ? 0 : type.length;
            }
            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.BLACK);
                colorTransitionPagerTitleView.setSelectedColor(Color.BLUE);
                colorTransitionPagerTitleView.setText(type[index]);
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        newsViewPager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }
            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        //将指示器与ViewPager绑定
        ViewPagerHelper.bind(magicIndicator, newsViewPager);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    private class NewsPagerAdapter extends FragmentPagerAdapter{

        public NewsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
           // return new NewsDetailFragment(position);
            return new NewsDetailFragment(typeEn[position]);
        }
        @Override
        public int getCount() {
            return type.length;
        }
    }
}
