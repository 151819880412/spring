package com.atguigu.spring6;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Admin
 * @Create: 2024/7/1 - 下午3:35
 * @Version: v1.0
 * ClassName: TestUser
 * Package: com.atguigu.spring6
 * Description: 描述
 */
public class TestUser {

    @Test
    public void testUser() {
        // 1. 加载 spring 配置对象, 创建 spring 容器对象
        ClassPathXmlApplicationContext contxt = new ClassPathXmlApplicationContext("bean.xml");
        // 2. 获取创建的对象
        User user = contxt.getBean("user", User.class);
        // 3. 使用对象调用方法进行测试
        System.out.println("user = " + user);
        user.sayHello();

        //  之前的写法
        // User user1 = new User();
        // user1.sayHello();
    }

    // 通过反射创建对象
    @Test
    public void testUserObject1 () throws Exception {
        //  获取 class 对象
        Class clazz = Class.forName("com.atguigu.spring6.User");
        //  调用方法创建对象
        Object user = clazz.newInstance();
        System.out.println("user = " + user);
    }
}
