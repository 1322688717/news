package com.ice.news.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ice.news.R;

public class NewsDetailActivity extends AppCompatActivity {

    private TextView newsTitleTextView;
    private TextView newsSubtitleTextView;
    private TextView newsDetailTextView;
    private ImageView newsImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        // 初始化视图
        newsTitleTextView = findViewById(R.id.newsTitleTextView);
        newsSubtitleTextView = findViewById(R.id.newsSubtitleTextView);
        newsDetailTextView = findViewById(R.id.newsDetailTextView);
        newsImageView = findViewById(R.id.newsImageView);

        // 获取传递过来的新闻数据
        String title = getIntent().getStringExtra("title");
        String subtitle = getIntent().getStringExtra("subtitle");
        String detail = getIntent().getStringExtra("detail");
        int imageResId = getIntent().getIntExtra("imageResId", R.mipmap.ic_launcher);

        // 设置新闻详情
        newsTitleTextView.setText(title);
        newsSubtitleTextView.setText(subtitle);
        newsDetailTextView.setText(detail);
//        newsImageView.setImageResource(imageResId);
    }
}
