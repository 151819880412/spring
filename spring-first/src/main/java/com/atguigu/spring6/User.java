package com.atguigu.spring6;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Admin
 * @Create: 2024/7/1 - 下午3:27
 * @Version: v1.0
 * ClassName: User
 * Package: com.atguigu.spring6
 * Description: 描述
 */
public class User {
    private Logger logger = LoggerFactory.getLogger(User.class);

    public void sayHello(){
        System.out.println("helloworld");
    }

    User() {
        System.out.println("User的无参构造方法");
        logger.debug("Hello log4j2!");
    }

}
