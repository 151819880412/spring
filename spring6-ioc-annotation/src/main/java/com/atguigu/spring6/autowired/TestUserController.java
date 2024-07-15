package com.atguigu.spring6.autowired;


import com.atguigu.spring6.autowired.controller.UserController;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Admin
 * @Create: 2024/7/15 - 下午3:16
 * @Version: v1.0
 * ClassName: TestUserController
 * Package: com.atguigu.spring6.autowired
 * Description: 描述
 */
public class TestUserController {

    public static void main(String[] args)
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        UserController userController = context.getBean("userController", UserController.class);
        userController.addUserController();
    }
}
