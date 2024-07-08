package com.atguigu.spring6.iocxml.dimap;

/**
 * @Author: Admin
 * @Create: 2024/7/5 - 下午4:01
 * @Version: v1.0
 * ClassName: Teacher
 * Package: com.atguigu.spring6.iocxml.dimap
 * Description: 描述
 */
public class Teacher {
    private String name;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
