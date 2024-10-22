package com.ice.news.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ice.news.R;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.FormBody;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnRegister; // 添加注册按钮
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister); // 初始化注册按钮

        // 初始化 OkHttpClient
        client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    System.out.println("Sending request: " + request.url() + " with headers " + request.headers());
                    return chain.proceed(request);
                })
                .addInterceptor(chain -> {
                    Response response = chain.proceed(chain.request());
                    System.out.println("Received response for: " + response.request().url() + " with response code " + response.code());
                    return response;
                })
                .build();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                // 检查输入是否为空
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 发送登录请求
                sendLoginRequest(username, password);
            }
        });

        // 注册按钮点击事件
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到注册页面
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void sendLoginRequest(String username, String password) {
        String url = "http://192.168.31.39:8080/auth/login"; // 替换为你的登录接口

        // 构建请求体
        FormBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(LoginActivity.this, "登录请求失败: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseData = response.body().string(); // 获取响应内容

                runOnUiThread(() -> {
                    // 检查响应内容
                    if (response.isSuccessful()) {
                        if (responseData.equals("Invalid username or password!")) {
                            // 如果响应体是"Invalid username or password!"，则显示相应提示
                            Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        } else {
                            // 处理成功响应
                            Toast.makeText(LoginActivity.this, "登录成功: " + responseData, Toast.LENGTH_SHORT).show();
                            // 跳转到 MainActivity
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish(); // 结束当前活动
                        }
                    } else {
                        // 处理其他失败响应
                        Toast.makeText(LoginActivity.this, "登录失败: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
    }
}
