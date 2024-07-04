package com.atguigu.spring6.iocxml.bean;

/**
 * @Author: Admin
 * @Create: 2024/7/4 - 下午4:07
 * @Version: v1.0
 * ClassName: UserDaoImpl
 * Package: com.atguigu.spring6.iocxml.bean
 * Description: 描述
 */
public class UserDaoImpl implements UserDao{

    @Override
    public void run(){
        System.out.println("UserDaoImpl run...");
    }
}
