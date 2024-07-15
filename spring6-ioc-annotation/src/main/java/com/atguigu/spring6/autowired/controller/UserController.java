package com.atguigu.spring6.autowired.controller;

import com.atguigu.spring6.autowired.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @Author: Admin
 * @Create: 2024/7/15 - 下午3:00
 * @Version: v1.0
 * ClassName: UserController
 * Package: com.atguigu.spring6.autowired
 * Description: 描述
 */
@Controller
public class UserController {
    //  注入 Service
    // 第一种方式:属性注入
    @Autowired  //  根据类型找到对应的对象并注入
    private UserService userService;
    public void addUserController()
    {
        System.out.println("Controller 调用 Service");
        userService.addUserService();
    }
}
