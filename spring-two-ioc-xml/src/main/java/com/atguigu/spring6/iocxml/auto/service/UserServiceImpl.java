package com.atguigu.spring6.iocxml.auto.service;
import com.atguigu.spring6.iocxml.auto.dao.UserDao;

/**
 * @Author: Admin
 * @Create: 2024/7/12 - 下午4:44
 * @Version: v1.0
 * ClassName: UserServiceImpl
 * Package: com.atguigu.spring6.iocxml.auto.service
 * Description: 描述
 */
public class UserServiceImpl implements UserService{

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUserService() {
        System.out.println("UserService---");
        // 调用 dao 中的方法
        userDao.addUserDao();
        // UserDaoImpl userDao = new UserDaoImpl();
        // userDao.addUserDao();
    }
}
