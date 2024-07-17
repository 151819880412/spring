package com.atguigu.dao.impl;

import com.atguigu.anno.Bean;
import com.atguigu.dao.UserDao;

/**
 * @Author: Admin
 * @Create: 2024/7/17 - 下午3:00
 * @Version: v1.0
 * ClassName: UserDaoImpl
 * Package: com.atguigu.dao.impl
 * Description: 描述
 */
@Bean
public class UserDaoImpl implements UserDao {
    @Override
    public void addUserDao() {
        System.out.println("dao........");
    }
}
