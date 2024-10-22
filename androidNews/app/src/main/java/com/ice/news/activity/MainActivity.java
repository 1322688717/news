package com.ice.news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ice.news.R;
import com.ice.news.fragment.NewsFragment;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private FloatingActionButton fabAddNews;
    private String[] tabTitles = {"科技", "体育", "娱乐", "财经"}; // Tab 标题数组

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        fabAddNews = findViewById(R.id.fabAddNews);

        // 设置 ViewPager 的适配器
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle()));

        // 将 TabLayout 和 ViewPager 联动
        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTitles[position]); // 设置 Tab 的标题
            }
        }).attach();

        // 设置按钮点击事件，跳转到增加新闻界面
        fabAddNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewsActivity.class);
                startActivity(intent);
            }
        });
    }

    // ViewPager2 适配器，管理不同新闻类别的 Fragment
    private class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            // 根据位置返回不同的 Fragment
            return NewsFragment.newInstance(tabTitles[position]);
        }

        @Override
        public int getItemCount() {
            return tabTitles.length; // Tab 的数量
        }
    }
}
