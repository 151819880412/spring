package com.atguigu.spring6.iocxml.auto.controller;

import com.atguigu.spring6.iocxml.auto.service.UserService;
import com.atguigu.spring6.iocxml.auto.service.UserServiceImpl;
import org.junit.jupiter.api.Test;

/**
 * @Author: Admin
 * @Create: 2024/7/12 - 下午4:41
 * @Version: v1.0
 * ClassName: UserController
 * Package: com.atguigu.spring6.iocxml.auto
 * Description: 描述
 */
public class UserController {

    public UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void addUserController(){
        System.out.println("Controller层调用了");
        // 调用 service 的方法
        userService.addUserService();

        // UserServiceImpl userService = new UserServiceImpl();
        // userService.addUserService();
    }
}
