package com.atguigu.spring6.iocxml.dimap;

/**
 * @Author: Admin
 * @Create: 2024/7/5 - 下午4:16
 * @Version: v1.0
 * ClassName: Lesson
 * Package: com.atguigu.spring6.iocxml.dimap
 * Description: 描述
 */
public class Lesson {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "name='" + name + '\'' +
                '}';
    }
}
