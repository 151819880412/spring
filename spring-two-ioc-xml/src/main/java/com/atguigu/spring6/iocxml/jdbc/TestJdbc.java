package com.atguigu.spring6.iocxml.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Author: Admin
 * @Create: 2024/7/8 - 下午4:19
 * @Version: v1.0
 * ClassName: TestJdbc
 * Package: com.atguigu.spring6.iocxml.jdbc
 * Description: 描述
 */
public class TestJdbc {

    @Test
    public void test() throws SQLException {
        // 方法一
        // DruidDataSource druidDataSource = new DruidDataSource();
        // druidDataSource.setUrl("jdbc:mysql://localhost:3306/atguigu?serverTimezone=UTC");
        // druidDataSource.setUsername("root");
        // druidDataSource.setPassword("qwert123");
        // druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        // System.out.println(druidDataSource.getConnection());

        // 方法二
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean-jdbc.xml");
        DruidDataSource druidDataSource = context.getBean(DruidDataSource.class);
        System.out.println(druidDataSource.getConnection());


    }
}
