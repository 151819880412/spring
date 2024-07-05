package com.atguigu.spring6.iocxml.ditest;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Admin
 * @Create: 2024/7/5 - 上午11:47
 * @Version: v1.0
 * ClassName: TestEmp
 * Package: com.atguigu.spring6.iocxml.ditest
 * Description: 描述
 */
public class TestEmp {
    public static void main(String[] args) {
        // 加载配置文件
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean-ditest.xml");
        // 通过 id 和 class 获取 bean
        Emp emp = context.getBean("emp", Emp.class);
        emp.work();
    }

}
