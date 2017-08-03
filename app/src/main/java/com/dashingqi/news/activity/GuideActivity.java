package com.dashingqi.news.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dashingqi.news.R;
import com.dashingqi.news.util.ConstantValues;
import com.dashingqi.news.util.SPUtils;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {

    private ViewPager vpGuide;
    private int [] guidePicArray = new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
    private ImageView ivGuiPic;
    //存储图片对象的集合
    private ArrayList<ImageView> ivGuiPicList =new ArrayList<>();
    //灰色小点
    private ImageView grayPoint;
    private Button btnStart;
    private LinearLayout llContain;
    //蓝色小点
    private ImageView ivBluePoint;
    //两个小点之间的距离
    private int twoPointDis;
    //小蓝点移动的距离
    private int bluePointMoveDis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        initData();
    }
    /**
     * 初始化数据
     */
    private void initData() {
        for (int i=0;i<guidePicArray.length;i++){
            ivGuiPic = new ImageView(this);
            ivGuiPic.setBackgroundResource(guidePicArray[i]);
            ivGuiPicList.add(ivGuiPic);

            //初始化灰色小点 设置背景
            grayPoint = new ImageView(this);
            grayPoint.setImageResource(R.drawable.shape_gray_point);


            //灰色小点 要设置到LinearLayout中的 所以 父控件为谁 这里就找谁
             LinearLayout.LayoutParams params =
                     new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT
                             ,LinearLayout.LayoutParams.WRAP_CONTENT);
             if(i>0){
                 params.leftMargin=10;  //从第二个小点开始 每个间隔 10dp
             }
             grayPoint.setLayoutParams(params);//设置小灰点的布局

            llContain.addView(grayPoint);//将小灰点设置到布局中
        }
        vpGuide.setAdapter(new GuideAdapter());
        vpGuide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 参数 第几个页面的位置  移动的百分比  移动的像素比
                //计算小蓝点移动距离 一直与最左边进行比对 ViewPager移动的百分比*两个小点之间的距离 + position*两个小点之间的距离；
                bluePointMoveDis = (int) ((positionOffset*twoPointDis)+(position*twoPointDis));
                RelativeLayout.LayoutParams bluePointParams  = (RelativeLayout.LayoutParams) ivBluePoint.getLayoutParams();
                bluePointParams.leftMargin = bluePointMoveDis;
                ivBluePoint.setLayoutParams(bluePointParams);

            }

            //viewpager 滑动完之后 回调方法
            @Override
            public void onPageSelected(int position) {
                if (position==guidePicArray.length-1){
                    btnStart.setVisibility(View.VISIBLE);
                }else{
                    btnStart.setVisibility(View.INVISIBLE);
                }
            }
            //viewpager 状态正在改变的回调方法
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //计算两个小点之间的巨离 视图树
        ivBluePoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ivBluePoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                twoPointDis = llContain.getChildAt(1).getLeft()-llContain.getChildAt(0).getLeft();
            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.setBoolean(getApplicationContext(), ConstantValues.IS_FIRST_ENTER,true);
                startActivity(new Intent(GuideActivity.this,MainActivity.class));
                finish();
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initView() {
        vpGuide = (ViewPager) findViewById(R.id.vp_guide);
        btnStart = (Button) findViewById(R.id.btn_start);
        llContain = (LinearLayout) findViewById(R.id.ll_contain);
        ivBluePoint = (ImageView) findViewById(R.id.iv_bluepoint);

    }


    /**
     * viewpager 设置数据
     */
    private class GuideAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return ivGuiPicList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = ivGuiPicList.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
        //git 版本测试
    }
}
