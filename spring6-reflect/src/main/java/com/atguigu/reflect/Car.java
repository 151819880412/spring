package com.atguigu.reflect;

/**
 * @Author: Admin
 * @Create: 2024/7/17 - 上午10:57
 * @Version: v1.0
 * ClassName: Car
 * Package: com.atguigu.reflect
 * Description: 描述
 */
public class Car {
    private String name;
    private int age;
    private String color;
    public String aa;

    private void run() {
        System.out.println("私有方法---run");
    }

    public Car() {
        System.out.println("无参构造方法");
    }

    public Car(String name, int age, String color) {
        this.name = name;
        this.age = age;
        this.color = color;
        System.out.println("有参构造方法");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", color='" + color + '\'' +
                '}';
    }

}
