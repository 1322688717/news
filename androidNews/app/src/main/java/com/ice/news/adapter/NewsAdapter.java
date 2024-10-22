package com.ice.news.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ice.news.R;
import com.ice.news.activity.NewsDetailActivity;
import com.ice.news.model.News;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<News> newsList;
    private Context context;

    // 修改构造函数，接收 Context 参数
    public NewsAdapter(Context context, List<News> newsList) {
        this.context = context; // 初始化 context
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News newsItem = newsList.get(position);
        holder.newsTitleTextView.setText(newsItem.getTitle());
        holder.newsSubtitleTextView.setText(newsItem.getSubtitle());
        holder.newsDetailTextView.setText(newsItem.getDetail());
//        holder.newsImageView.setImageResource(newsItem.getImageResId());

        // 为每个新闻项设置点击事件监听
        holder.itemView.setOnClickListener(v -> {
            // 创建 Intent，跳转到 NewsDetailActivity 并传递新闻数据
            Intent intent = new Intent(context, NewsDetailActivity.class);
            intent.putExtra("title", newsItem.getTitle());
            intent.putExtra("subtitle", newsItem.getSubtitle());
            intent.putExtra("detail", newsItem.getDetail());
            intent.putExtra("imageResId", newsItem.getImageResId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {

        ImageView newsImageView;
        TextView newsTitleTextView;
        TextView newsSubtitleTextView;
        TextView newsDetailTextView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsImageView = itemView.findViewById(R.id.newsImageView);
            newsTitleTextView = itemView.findViewById(R.id.newsTitleTextView);
            newsSubtitleTextView = itemView.findViewById(R.id.newsSubtitleTextView);
            newsDetailTextView = itemView.findViewById(R.id.newsDetailTextView);
        }
    }
}
