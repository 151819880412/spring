package com.atguigu.spring6.iocxml.factoryBean;

import org.springframework.beans.factory.FactoryBean;

/**
 * @Author: Admin
 * @Create: 2024/7/12 - 下午4:00
 * @Version: v1.0
 * ClassName: MyFactorBean
 * Package: com.atguigu.spring6.iocxml.factorBean
 * Description: 描述
 */
public class MyFactoryBean implements FactoryBean<User> {
    @Override
    public User getObject() throws Exception {
        return new User();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}
// public class MyFactoryBean  {
//
// }
