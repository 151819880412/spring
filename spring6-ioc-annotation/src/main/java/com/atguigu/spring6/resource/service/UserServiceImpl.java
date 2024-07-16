package com.atguigu.spring6.resource.service;

import com.atguigu.spring6.resource.dao.UserDao;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author: Admin
 * @Create: 2024/7/15 - 下午4:32
 * @Version: v1.0
 * ClassName: UserServiceImpl
 * Package: com.atguigu.spring6.resource.service
 * Description: 描述
 */
@Service("myUserService")
public class UserServiceImpl implements UserService {

    // 不指定名称就根据属性名称进行注入
    @Resource
    UserDao myUserDao;

    @Override
    public void addUserService() {
        System.out.println("Service 调用 Dao");
        myUserDao.addUserDao();
    }
}
