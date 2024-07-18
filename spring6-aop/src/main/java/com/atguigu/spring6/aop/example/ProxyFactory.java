package com.atguigu.spring6.aop.example;

import org.aopalliance.intercept.Invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author: Admin
 * @Create: 2024/7/18 - 上午11:12
 * @Version: v1.0
 * ClassName: ProxyFactory
 * Package: com.atguigu.spring6.aop.example
 * Description: 动态代理
 */
public class ProxyFactory {
    // 目标对象
    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    // 返回代理对象
    public Object getProxy() {
        /*
         * Proxy.newProxyInstance() 有三个参数
         * 1. ClassLoader 加载动态生成代理类的加载器
         * 2. Class<?>[] interfaces 目标对象实现的所有接口的 class 类型数组
         * 3. InvocationHandler 设置代理对象实现目标方法的过程
         * */
        ClassLoader classLoader = target.getClass().getClassLoader();
        Class<?>[] interfaces = target.getClass().getInterfaces();

        InvocationHandler invocationHandler = new InvocationHandler() {
            /*
            * 1. 代理对象
            * 2. 需要重写目标对象的方法
            * 3. method 方法里面的参数
            * */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 调用目标方法
                System.out.println("方法之前----------");
                Object result = method.invoke(target, args);
                System.out.println("方法之后----------");
                return result;
            }
        };
        return Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
    }
}
