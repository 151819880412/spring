package com.atguigu.spring6.resource;

import com.atguigu.spring6.config.springConfig;
import com.atguigu.spring6.resource.controller.UserController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: Admin
 * @Create: 2024/7/17 - 上午10:37
 * @Version: v1.0
 * ClassName: TestUserControllerAnno
 * Package: com.atguigu.spring6.resource
 * Description: 描述
 */
public class TestUserControllerAnno {
    public static void main(String[] args) {
        //  加载配置类
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(springConfig.class);
        UserController userController = annotationConfigApplicationContext.getBean("userController", UserController.class);
        userController.addUserController();

    }
}
