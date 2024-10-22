package com.ice.news.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ice.news.R;
import com.ice.news.adapter.NewsAdapter;
import com.ice.news.model.News;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewsFragment extends Fragment {

    private static final String ARG_NEWS_TYPE = "news_type";
    private String newsType;
    private OkHttpClient client; // 声明 OkHttpClient

    public static NewsFragment newInstance(String newsType) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NEWS_TYPE, newsType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            newsType = getArguments().getString(ARG_NEWS_TYPE);
        }
        client = new OkHttpClient(); // 初始化 OkHttpClient
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 设置下拉刷新监听器
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // 重新加载新闻数据
            loadNews(recyclerView, swipeRefreshLayout);
            swipeRefreshLayout.setRefreshing(false); // 停止刷新动画
        });

        loadNews(recyclerView,swipeRefreshLayout); // 初始加载新闻
        return view;
    }


    private void loadNews(RecyclerView recyclerView, SwipeRefreshLayout swipeRefreshLayout) {
        fetchNewsFromApi(newsType, recyclerView, swipeRefreshLayout);
    }


    private void fetchNewsFromApi(String newsType, RecyclerView recyclerView, SwipeRefreshLayout swipeRefreshLayout) {
        String json = "{\"type\": \"" + newsType + "\"}";
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));

        Request request = new Request.Builder()
                .url("http://192.168.31.39:8080/news/type") // 替换为你的服务器地址
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "加载新闻失败: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    showEmptyView(recyclerView); // 显示空视图
                    swipeRefreshLayout.setRefreshing(false); // 停止刷新动画
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    List<News> newsList = parseNews(responseData);

                    getActivity().runOnUiThread(() -> {
                        if (newsList == null || newsList.isEmpty()) {
                            showEmptyView(recyclerView); // 显示空视图
                        } else {
                            NewsAdapter adapter = new NewsAdapter(getContext(), newsList);
                            recyclerView.setAdapter(adapter);
                            Toast.makeText(getContext(), "新闻刷新成功", Toast.LENGTH_SHORT).show(); // 刷新成功提示
                        }
                        swipeRefreshLayout.setRefreshing(false); // 停止刷新动画
                    });
                } else {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "加载新闻失败: " + response.code(), Toast.LENGTH_SHORT).show();
                        showEmptyView(recyclerView); // 显示空视图
                        swipeRefreshLayout.setRefreshing(false); // 停止刷新动画
                    });
                }
            }

        });
    }


    private List<News> parseNews(String jsonData) {
        Gson gson = new Gson();
        Type newsListType = new TypeToken<List<News>>(){}.getType();
        return gson.fromJson(jsonData, newsListType);
    }

    private void showEmptyView(RecyclerView recyclerView) {
        recyclerView.setVisibility(View.GONE); // 隐藏 RecyclerView
        // 你可以在这里显示一个 TextView 或其他视图来提示用户
    }

}
