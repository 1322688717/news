package com.ice.news.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.ice.news.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
public class AddNewsActivity extends AppCompatActivity {
    private EditText etMainTitle, etSubtitle, etDetails;
    private Button btnChooseLogo, btnAddNews;
    private Spinner spinnerNewsType;
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        etMainTitle = findViewById(R.id.et_main_title);
        etSubtitle = findViewById(R.id.et_subtitle);
        etDetails = findViewById(R.id.et_details);
        btnChooseLogo = findViewById(R.id.btn_choose_logo);
        btnAddNews = findViewById(R.id.btn_add_news);
        spinnerNewsType = findViewById(R.id.spinner_news_type);

        client = new OkHttpClient();

        btnAddNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNews();
            }
        });
    }

    private void addNews() {
        String mainTitle = etMainTitle.getText().toString();
        String subtitle = etSubtitle.getText().toString();
        String details = etDetails.getText().toString();
        String newsType = spinnerNewsType.getSelectedItem().toString();
        String logo = "default_logo"; // 使用实际的 logo 地址

        if (TextUtils.isEmpty(mainTitle) || TextUtils.isEmpty(subtitle) || TextUtils.isEmpty(details)) {
            Toast.makeText(this, "请填写所有字段", Toast.LENGTH_SHORT).show();
            return;
        }

        // 构建请求体
        FormBody formBody = new FormBody.Builder()
                .add("title", mainTitle)
                .add("subtitle", subtitle)
                .add("details", details)
                .add("type", newsType)
                .add("logo", logo) // 使用实际的 logo 地址
                .build();

        // 构建请求
        Request request = new Request.Builder()
                .url("http://192.168.31.39:8080/news/add")
                .post(formBody)
                .build();

        // 发送请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> Toast.makeText(AddNewsActivity.this, "新闻添加失败: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    runOnUiThread(() -> {
                        Toast.makeText(AddNewsActivity.this, "新闻添加成功", Toast.LENGTH_SHORT).show();
                        finish(); // 结束当前活动，返回上一界面
                    });
                } else {
                    int code = response.code(); // 获取状态码
                    String message = response.message(); // 获取错误信息
                    runOnUiThread(() -> Toast.makeText(AddNewsActivity.this, "新闻添加失败: " + code + " - " + message, Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}
