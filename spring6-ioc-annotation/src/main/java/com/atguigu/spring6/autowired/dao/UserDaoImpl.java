package com.atguigu.spring6.autowired.dao;

import org.springframework.stereotype.Repository;

/**
 * @Author: Admin
 * @Create: 2024/7/15 - 下午3:02
 * @Version: v1.0
 * ClassName: UserDaoImpl
 * Package: com.atguigu.spring6.autowired.dao
 * Description: 描述
 */
@Repository
public class UserDaoImpl implements UserDao{

    @Override
    public void addUserDao() {
        System.out.println("dao层添加用户");
    }
}
