package com.springforandroid.demo3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // 修改register方法以接受键值对
    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        // 创建用户对象
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        // 调用服务层进行注册
        userService.register(user);
        return "User registered successfully!";
    }

    // 登录方法保持不变
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        User user = userService.login(username, password);
        if (user != null) {
            return "Login successful!";
        }
        return "Invalid username or password!";
    }
}
