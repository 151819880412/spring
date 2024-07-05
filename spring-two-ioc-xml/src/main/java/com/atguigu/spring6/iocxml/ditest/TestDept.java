package com.atguigu.spring6.iocxml.ditest;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Admin
 * @Create: 2024/7/5 - 下午3:51
 * @Version: v1.0
 * ClassName: TestDept
 * Package: com.atguigu.spring6.iocxml.ditest
 * Description: 描述
 */
public class TestDept {

    public static void main(String[] args) {
        // 加载配置文件
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean-dilist.xml");
        // 通过 id 和 class 获取 bean
        Dept emp = context.getBean("dept", Dept.class);
        emp.info();
    }
}
