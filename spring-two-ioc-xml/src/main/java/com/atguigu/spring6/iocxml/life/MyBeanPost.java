package com.atguigu.spring6.iocxml.life;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;

/**
 * @Author: Admin
 * @Create: 2024/7/12 - 下午3:53
 * @Version: v1.0
 * ClassName: MyBeanPost
 * Package: com.atguigu.spring6.iocxml.life
 * Description: 描述
 */
public class MyBeanPost implements BeanPostProcessor {
    @Override
    @Nullable
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("3. bean 后置处理器(初始化之前执行)");
        return bean;
    }

    @Override
    @Nullable
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("5. bean 后置处理器(初始化之后执行)");
        return bean;
    }

}
