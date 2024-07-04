package com.atguigu.spring6.iocxml.bean;

/**
 * @Author: Admin
 * @Create: 2024/7/4 - 下午4:13
 * @Version: v1.0
 * ClassName: PersonDao
 * Package: com.atguigu.spring6.iocxml.bean
 * Description: 描述
 */
public class PersonDaoImpl implements UserDao {
    @Override
    public void run() {
        System.out.println("PersonDaoImpl run...");
    }
}
