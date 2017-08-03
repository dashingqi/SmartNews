package com.dashingqi.glidetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url = "http://i.imgur.com/DvpvklR.png";
        image = (ImageView) findViewById(R.id.iv_pic);
        Glide.with(this).load(url).into(image);

    }
}
