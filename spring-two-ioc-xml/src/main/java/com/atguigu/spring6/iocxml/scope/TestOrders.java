package com.atguigu.spring6.iocxml.scope;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Admin
 * @Create: 2024/7/8 - 下午5:24
 * @Version: v1.0
 * ClassName: TestOrders
 * Package: com.atguigu.spring6.iocxml.scope
 * Description: 描述
 */
public class TestOrders {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean-scope.xml");
        Orders orders1 = context.getBean("orders", Orders.class);
        Orders orders2 = context.getBean("orders", Orders.class);
        System.out.println(orders1);
        System.out.println(orders2);

    }
}
