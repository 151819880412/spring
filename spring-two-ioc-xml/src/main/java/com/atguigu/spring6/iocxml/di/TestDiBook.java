package com.atguigu.spring6.iocxml.di;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Admin
 * @Create: 2024/7/5 - 上午10:03
 * @Version: v1.0
 * ClassName: TestDiBook
 * Package: com.atguigu.spring6.iocxml.di
 * Description: 描述
 */
public class TestDiBook {

    @Test
    public void testSet(){
        /*具体流程如下：
            Spring 根据配置文件中 <bean> 元素的 class 属性找到 Book 类。
            Spring 使用无参构造器实例化 Book 对象，这时会输出 "book无参构造器"。
            Spring 调用 setName 和 setAuthor 方法注入 name 和 author 属性。*/
        // 加载配置文件
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean-di.xml");
        // 根据 id 和类型获取 bean
        Book book = context.getBean("book", Book.class);
        System.out.println(book);

    }

    @Test
    public void testConstructor(){
        /*具体流程如下：
            Spring 根据配置文件中 <bean> 元素的 class 属性找到 Book 类。
            Spring 使用有参构造器实例化 Book 对象，这时会输出 "book有参构造器"。
            Spring 通过有参构造器注入 name 和 author 属性。*/
        // 加载配置文件
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean-di.xml");
        // 根据 id 和类型获取 bean
        Book book = context.getBean("bookCon", Book.class);
        System.out.println(book);

    }
}
