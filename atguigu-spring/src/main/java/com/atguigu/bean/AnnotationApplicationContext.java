package com.atguigu.bean;

import com.atguigu.anno.Bean;
import com.atguigu.anno.Di;
import com.atguigu.service.impl.UserServiceImpl;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Admin
 * @Create: 2024/7/17 - 下午3:11
 * @Version: v1.0
 * ClassName: AnnotationApplicationContext
 * Package: com.atguigu.bean
 * Description: 描述
 */
public class AnnotationApplicationContext implements ApplicationContext {

    // 创建 map 集合存放 bean 对象
    private HashMap<Class, Object> beanFactory = new HashMap<>();
    private String rootPath;

    //  返回对象
    @Override
    public Object getBean(Class clazz) {
        return beanFactory.get(clazz);
    }

    //  创建有参数构造,传递包路径,设置扫描规则
    //  当前包以及子包,哪个类有 @bean 注解,把这个类通过反射实例化
    public AnnotationApplicationContext(String basePackage) {
        try {
            //  com.atguigu
            //  1. 把 . 替换为 /
            String packagePath = basePackage.replaceAll("\\.", "\\\\");
            System.out.println(packagePath);
            //  2. 获取包的绝对路径
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(packagePath);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                String filePath = URLDecoder.decode(url.getFile(), "utf-8");
                System.out.println(filePath);
                // 获取包前面路径部分,字符串截取
                rootPath = filePath.substring(0, filePath.length() - packagePath.length());
                // 包扫描
                loadBean(new File(filePath));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 依赖注入
        loadDi();
    }

    // 包扫描过程,实例化
    private void loadBean(File file) throws Exception {
        //  1. 判断当前是否文件夹
        if (file.isDirectory()) {
            //  2. 获取文件夹里面所有内容
            File[] files = file.listFiles();
            //  3. 判断文件夹为空,直接返回
            if (files == null || files.length == 0) {
                return;
            }
            //  4. 如果文件夹不为空,遍历文件夹所有的内容
            for (File child : files) {
                //  4.1 遍历得到的 File 对象,继续判断,如果还是文件夹,递归
                if (child.isDirectory()) {
                    // 递归
                    loadBean(child);
                } else {
                    //  4.2 遍历得到的 File 对象不是文件夹,是文件
                    //  4.3 得到包路径+类名称部分 -- 字符串截取过程
                    String pathWithClass = child.getAbsolutePath().substring(rootPath.length() - 1);
                    System.out.println("pathWithClass------" + pathWithClass);
                    //  4.4 判断当前文件类型是否 .class
                    if (pathWithClass.contains(".class")) {
                        //  4.5 如果是 .class,把路径的 \ 替换成 . 把.class去掉,得到类名称
                        String className = pathWithClass.replaceAll("\\\\", ".").replace(".class", "");
                        //  4.6 判断类是否有 @bean 注解,如果有就实例化
                        //  4.6.1 获取类的 class
                        Class<?> clazz = Class.forName(className);
                        //  4.6.2 判断不是接口才实例化
                        if (!clazz.isInterface()) {
                            //  4.6.3 判断类上面是否有注解 @bean
                            Bean annotationPresent = clazz.getAnnotation(Bean.class);
                            if (annotationPresent != null) {
                                //  4.6.4 实例化
                                Object instance = clazz.getDeclaredConstructor().newInstance();
                                //  4.7 把实例化的对象放入到 beanFactory 集合中
                                //  4.7.1 判断当前类如果有接口,就让接口的 class 作为 map 的 key
                                if (clazz.getInterfaces().length > 0) {
                                    beanFactory.put(clazz.getInterfaces()[0], instance);
                                } else {
                                    beanFactory.put(clazz, instance);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void loadDi() {
        // 实例化对象在 beanFactory 的 map 集合里面
        // 1. 遍历 beanFactory 的 map 集合
        // 2. 获取 map 集合的每个对象的 key 每个对象属性获取到
        // 3. 遍历得到每个对象啊的属性数组,得到每个属性
        // 4. 判断属性上是否有 @Di 注解
        // 4.1 如果有私有属性,设置属性可访问
        // 5. 如果有,就从 beanFactory 集合中获取到对应的对象,设置到属性上
        Set<Map.Entry<Class, Object>> entries = beanFactory.entrySet();
        // 1. 遍历 beanFactory 的 map 集合
        for (Map.Entry<Class, Object> entry : entries) {
            // 2. 获取 map 集合的每个对象的 key 每个对象属性获取到
            Object obj = entry.getValue();
            // 获取对象 class
            Class<?> clazz = obj.getClass();
            //  获取每个对象属性获取到
            Field[] declaredFields = clazz.getDeclaredFields();
            // 3. 遍历得到每个对象啊的属性数组,得到每个属性
            for (Field field : declaredFields) {
                // 4. 判断属性上是否有 @Di 注解
                Di annotation = field.getAnnotation(Di.class);
                // 如果有私有属性,设置属性可访问
                if (annotation != null) {
                    // 4.1 如果有私有属性,设置属性可访问
                    field.setAccessible(true);
                    // 5. 如果有,就从 beanFactory 集合中获取到对应的对象,设置到属性上
                    try {
                        System.out.println("正在给【" + obj.getClass().getName() + "】属性【" + field.getName() + "】注入值【" + beanFactory.get(field.getType()).getClass().getName() + "】");
                        field.set(obj, beanFactory.get(field.getType()));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        // for(Map.Entry<Class,Object> entry : beanFactory.entrySet()){
        //     //就是咱们放在容器的对象
        //     Object obj = entry.getValue();
        //     Class<?> aClass = obj.getClass();
        //     Field[] declaredFields = aClass.getDeclaredFields();
        //     for (Field field : declaredFields){
        //         Di annotation = field.getAnnotation(Di.class);
        //         if( annotation != null ){
        //             field.setAccessible(true);
        //             try {
        //                 System.out.println("正在给【"+obj.getClass().getName()+"】属性【" + field.getName() + "】注入值【"+ beanFactory.get(field.getType()).getClass().getName() +"】");
        //                 field.set(obj,beanFactory.get(field.getType()));
        //             } catch (IllegalAccessException e) {
        //                 e.printStackTrace();
        //             }
        //         }
        //     }
        // }
    }
}
