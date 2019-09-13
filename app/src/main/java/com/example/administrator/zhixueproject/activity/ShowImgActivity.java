package com.example.administrator.zhixueproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.zhixueproject.R;
import com.example.administrator.zhixueproject.view.TouchImageView;

/**
 * Created by Administrator on 2019/9/13.
 */

public class ShowImgActivity extends BaseActivity {

    private TouchImageView bigImage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showimg);

        bigImage=(TouchImageView)findViewById(R.id.img_big_photo);
        TextView tvHead=(TextView)findViewById(R.id.tv_title);
        tvHead.setText("图片展示");
        TextView tvRight=(TextView)findViewById(R.id.tv_right);
        tvRight.setText("确定发送");
        findViewById(R.id.lin_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowImgActivity.this.finish();
            }
        });
        tvRight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setResult(0x00a,new Intent());
                ShowImgActivity.this.finish();
            }
        });

        String imgPath=getIntent().getStringExtra("imgPath");
        Glide.with(ShowImgActivity.this).load(imgPath).diskCacheStrategy(DiskCacheStrategy.NONE).into(bigImage);

    }
}
