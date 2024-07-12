package com.atguigu.spring6.iocxml.factoryBean;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Admin
 * @Create: 2024/7/12 - 下午4:16
 * @Version: v1.0
 * ClassName: TestUser
 * Package: com.atguigu.spring6.iocxml.factoryBean
 * Description: 描述
 */
public class TestUser {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean-factorybean.xml");
        User user = context.getBean("user", User.class);
        // MyFactoryBean user = context.getBean("user", MyFactoryBean.class);
        System.out.println(user);
    }
}
