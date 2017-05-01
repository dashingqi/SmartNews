package com.dashingqi.news.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.widget.TextView;

import com.dashingqi.news.R;
import com.dashingqi.news.util.ConstantValues;
import com.dashingqi.news.util.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.tvSplash)
    TextView tvSplash;

    //定义属性动画的集合
    private AnimatorSet set;
    private ObjectAnimator translationX;
    private ObjectAnimator translationY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //安卓4.4版本以上 去掉状态栏
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            //状态栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //导航栏透明
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_splash);
        //采用butterknife 以注解的方式 findviewbyid
        ButterKnife.bind(this);

        //初始化属性动画集合
        set = new AnimatorSet();
        translationX = ObjectAnimator.ofFloat(tvSplash, "translationX", 600, 0);
        translationY = ObjectAnimator.ofFloat(tvSplash, "translationY", -100, 90, -80, 70, -60, 50);

        set.playTogether(translationX, translationY);
        set.setDuration(2000);

        monitorAnimator();

    }

    /**
     * 监听动画
     */
    private void monitorAnimator() {
        //启动动画
       set.start();
        //设置监听事件
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                boolean is_first_enter = SPUtils.getBoolean(getApplicationContext(), ConstantValues.IS_FIRST_ENTER, false);
                if (is_first_enter){
                    try {
                        Thread.sleep(500);
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    startActivity(new Intent(getApplicationContext(),GuideActivity.class));
                }
                finish();

            }
            @Override
            public void onAnimationCancel(Animator animation) {

            }
            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
