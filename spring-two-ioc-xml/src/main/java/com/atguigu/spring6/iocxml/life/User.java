package com.atguigu.spring6.iocxml.life;

/**
 * @Author: Admin
 * @Create: 2024/7/12 - 下午3:15
 * @Version: v1.0
 * ClassName: User
 * Package: com.atguigu.spring6.iocxml.life
 * Description: 描述
 */
public class User {
    private String name;

    //  无参构造
    public User() {
        System.out.println("1. bean 对象创建(调用无参构造)");
    }


    public void setName(String name) {
        System.out.println("2. 给 bean 对象设置相关属性");
        this.name = name;
    }

    // 初始化的方法
    public void init() {
        System.out.println("4. bean 初始化(调用指定初始化的方法)");
    }

    // 销毁的方法
    public void destroy() {
        System.out.println("7. bean 对象销毁(指定销毁的方法)");
    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
