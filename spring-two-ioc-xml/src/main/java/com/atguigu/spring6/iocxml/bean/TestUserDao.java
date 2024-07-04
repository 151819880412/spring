package com.atguigu.spring6.iocxml.bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Admin
 * @Create: 2024/7/4 - 下午4:10
 * @Version: v1.0
 * ClassName: TestUserDao
 * Package: com.atguigu.spring6.iocxml.bean
 * Description: 描述
 */
public class TestUserDao {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        // 根据接口获取接口对应的 bean
        // 要求只能有一个实现类,多个实现类会报错
        // UserDao userDao = context.getBean( UserDao.class);
        // System.out.println(userDao);

        // 根据 id 和类型获取 bean
        UserDao userDao = context.getBean("userDao", UserDao.class);
        UserDao personDao = context.getBean("personDao", UserDao.class);

        System.out.println(userDao);
        System.out.println(personDao);
    }
}
