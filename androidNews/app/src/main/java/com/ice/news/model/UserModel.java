package com.ice.news.model;

public class UserModel {
    private String username;
    private String password;

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // 获取用户名
    public String getUsername() {
        return username;
    }

    // 获取密码
    public String getPassword() {
        return password;
    }

    // 验证用户名和密码是否有效（简单示例）
    public boolean isValid() {
        return username != null && !username.isEmpty() &&
                password != null && !password.isEmpty();
    }
}
