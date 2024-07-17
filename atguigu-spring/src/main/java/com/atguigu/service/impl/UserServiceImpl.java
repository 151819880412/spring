package com.atguigu.service.impl;

import com.atguigu.anno.Bean;
import com.atguigu.anno.Di;
import com.atguigu.dao.UserDao;
import com.atguigu.service.UserService;

/**
 * @Author: Admin
 * @Create: 2024/7/17 - 下午2:59
 * @Version: v1.0
 * ClassName: UserServiceImpl
 * Package: com.atguigu.service.impl
 * Description: 描述
 */
@Bean
public class UserServiceImpl implements UserService {
    @Di
    private UserDao userDao;

    @Override
    public void addUserService() {
        System.out.println("service........");
        userDao.addUserDao();
    }
}
