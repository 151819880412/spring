package com.atguigu.reflect;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author: Admin
 * @Create: 2024/7/17 - 上午11:03
 * @Version: v1.0
 * ClassName: TestCar
 * Package: com.atguigu.reflect
 * Description: 描述
 */
public class TestCar {

    //  1. 获取 class 对象的多种方式
    @Test
    public void test1() throws Exception {
        //  1. 类名.class
        Class<Car> c1 = Car.class;
        System.out.println(c1);

        //  2. 对象.getClass()
        Car car = new Car();
        Class<? extends Car> c2 = car.getClass();
        System.out.println(c2);

        //  3. Class.forName("全类名")
        Class<?> c3 = Class.forName("com.atguigu.reflect.Car");
        System.out.println(c3);

        //  实例化
        Car car1 = (Car) c3.getDeclaredConstructor().newInstance();
        System.out.println(car1);
    }

    //  2. 获取构造方法
    @Test
    public void test2() throws Exception {
        Class<Car> clazz = Car.class;

        //  获取所有构造
        // getConstructors()    只能获取所有 public 的构造方法
        // Constructor<?>[] constructors = c.getConstructors();
        // for (Constructor<?> constructor : constructors) {
        //     System.out.println("方法名称：" + constructor.getName() + "参数个数：" + constructor.getParameterCount());
        // }

        // getDeclaredConstructors()    获取所有的构造方法
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        for (Constructor<?> declaredConstructor : declaredConstructors) {
            System.out.println("方法名称：" + declaredConstructor.getName() + "参数个数：" + declaredConstructor.getParameterCount());
        }

        //  指定有参数构造创建对象

        //  1. 构造 public
        // Constructor<Car> c1 = clazz.getDeclaredConstructor(String.class, int.class, String.class);
        // Car car1 = c1.newInstance("兰博基尼", 2023, "红色");
        // System.out.println(car1);

        //  2. 构造 private
        Constructor<Car> c2 = clazz.getDeclaredConstructor(String.class, int.class, String.class);
        c2.setAccessible(true);
        Car car2 = c2.newInstance("兰博基尼", 2023, "红色");
        System.out.println(car2);

    }

    //  3. 获取属性
    @Test
    public void test3() throws Exception {
        Class<Car> clazz = Car.class;
        Car car = clazz.getDeclaredConstructor().newInstance();
        //  获取所有 public 属性
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            System.out.println("public--" + field.getName());
        }
        //  获取所有属性
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getName().equals("name")) {
                // 设置允许访问
                field.setAccessible(true);
                field.set(car, "兰博基尼");
            }
            System.out.println("all--" + field.getName());
        }
        System.out.println(car);
    }

    //  4. 获取方法
    @Test
    public void test4() throws Exception {
        Car car = new Car("bbc", 2023, "红色");
        Class<? extends Car> clazz = car.getClass();
        //  1. public 方法
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            // System.out.println("public--"+method.getName());
            //  执行方法 toString
            if ("toString".equals(method.getName())) {
                String invoke = (String) method.invoke(car);
                System.out.println(invoke);
            }
        }
        //  2. private 方法
        Method[] methodsAll = clazz.getDeclaredMethods();
        for (Method declaredMethod : methodsAll) {
            // System.out.println("private--"+declaredMethod.getName());
            if ("run".equals(declaredMethod.getName())) {
                // 设置允许访问
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(car);
            }
        }
    }
}
