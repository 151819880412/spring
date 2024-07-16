package com.atguigu.spring6.resource.controller;

import com.atguigu.spring6.resource.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

/**
 * @Author: Admin
 * @Create: 2024/7/15 - 下午4:31
 * @Version: v1.0
 * ClassName: UserController
 * Package: com.atguigu.spring6.resource.controller
 * Description: 描述
 */

@Controller
public class UserController {

    // 根据名称进行注入
    @Resource(name = "myUserService")
    private UserService userService;

    public void addUserController()
    {
        System.out.println("Controller 调用 Service");
        userService.addUserService();
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        UserController userController = context.getBean("userController", UserController.class);
        userController.addUserController();

    }
}
