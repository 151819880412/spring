package com.atguigu.spring6.autowired.service;

import com.atguigu.spring6.autowired.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Admin
 * @Create: 2024/7/15 - 下午3:01
 * @Version: v1.0
 * ClassName: UserServiceImpl
 * Package: com.atguigu.spring6.autowired.service
 * Description: 描述
 */
@Service
public class UserServiceImpl implements UserService {
    // 注入 dao
    // 第一种方式:属性注入
    // @Autowired  //  根据类型找到对应的对象并注入
    // private UserDao userDao;

    // 第二种方式: set 注入
    // private UserDao userDao;
    // @Autowired
    // public void setUserDao(UserDao userDao) {
    //     this.userDao = userDao;
    // }

    //  场景三：构造方法注入
    // private UserDao userDao;
    //
    // @Autowired
    // public UserServiceImpl(UserDao userDao) {
    //     this.userDao = userDao;
    // }

    // 场景四：形参上注入
    private UserDao userDao;

    public UserServiceImpl(@Autowired UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUserService() {
        System.out.println("Service 调用 Dao");
        userDao.addUserDao();
    }
}
