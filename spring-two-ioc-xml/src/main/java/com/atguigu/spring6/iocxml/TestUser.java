package com.atguigu.spring6.iocxml;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Admin
 * @Create: 2024/7/4 - 上午10:03
 * @Version: v1.0
 * ClassName: TestUser
 * Package: com.atguigu.spring6.iocxml
 * Description: 描述
 */
public class TestUser {

   public static void main(String[] args){
       ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
       // 根据 id 获取 bean
       User user = context.getBean("user", User.class);
       System.out.println(user);

       // 根据类型获取 bean
       User user1 = context.getBean(User.class);
       System.out.println(user1);

       // 根据 id 和类型获取 bean
       User user2 = context.getBean("user", User.class);
       System.out.println(user2);
   }
}
