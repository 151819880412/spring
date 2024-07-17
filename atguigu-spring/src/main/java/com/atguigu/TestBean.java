package com.atguigu;

import com.atguigu.bean.AnnotationApplicationContext;
import com.atguigu.service.UserService;

/**
 * @Author: Admin
 * @Create: 2024/7/17 - 下午5:05
 * @Version: v1.0
 * ClassName: TestBean
 * Package: com.atguigu
 * Description: 描述
 */
public class TestBean {
    public static void main(String[] args) {
        AnnotationApplicationContext context = new AnnotationApplicationContext("com.atguigu");
        UserService userService = (UserService)context.getBean(UserService.class);
        userService.addUserService();
    }
}
