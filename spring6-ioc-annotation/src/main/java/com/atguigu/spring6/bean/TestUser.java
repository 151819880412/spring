package com.atguigu.spring6.bean;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Admin
 * @Create: 2024/7/15 - 下午2:33
 * @Version: v1.0
 * ClassName: TestUser
 * Package: com.atguigu.spring6.bean
 * Description: 描述
 */
public class TestUser {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        User user = context.getBean("user", User.class);
        System.out.println(user);
    }
}
