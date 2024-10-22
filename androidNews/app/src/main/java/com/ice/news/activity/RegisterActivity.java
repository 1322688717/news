package com.ice.news.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ice.news.R;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etRegisterUsername);
        etPassword = findViewById(R.id.etRegisterPassword);
        btnRegister = findViewById(R.id.btnPerformRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        // 检查输入是否为空
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpClient client = new OkHttpClient();

        FormBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url("http://192.168.31.39:8080/auth/register")
                .post(formBody)
                .build();

        // 打印请求信息
        Log.d("RegisterActivity", "Sending register request to: " + request.url() + " with username: " + username);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("RegisterActivity", "Register request failed", e);
                runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "网络错误", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // 处理成功响应
                    runOnUiThread(() -> {
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        // 可以选择在注册成功后跳转到登录页面
                        // Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        // startActivity(intent);
                         finish(); // 结束当前活动
                    });
                } else {
                    Log.e("RegisterActivity", "Register request failed with response code: " + response.code());
                    runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}
