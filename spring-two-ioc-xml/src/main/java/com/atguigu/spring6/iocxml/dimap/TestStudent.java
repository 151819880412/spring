package com.atguigu.spring6.iocxml.dimap;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Admin
 * @Create: 2024/7/5 - 下午4:08
 * @Version: v1.0
 * ClassName: TestStudent
 * Package: com.atguigu.spring6.iocxml.dimap
 * Description: 描述
 */
public class TestStudent {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean-diref.xml");
        Student student = context.getBean("student", Student.class);
        student.run();
    }
}
