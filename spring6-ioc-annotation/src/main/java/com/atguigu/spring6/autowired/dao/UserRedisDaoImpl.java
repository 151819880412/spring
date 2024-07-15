package com.atguigu.spring6.autowired.dao;

import org.springframework.stereotype.Repository;

/**
 * @Author: Admin
 * @Create: 2024/7/15 - 下午4:07
 * @Version: v1.0
 * ClassName: UserRedisDaoImpl
 * Package: com.atguigu.spring6.autowired.dao
 * Description: 描述
 */
@Repository
public class UserRedisDaoImpl implements UserDao{
    @Override
    public void addUserDao() {
        System.out.println("dao redis .......");
    }
}
