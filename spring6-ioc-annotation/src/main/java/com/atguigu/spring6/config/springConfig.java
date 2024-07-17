package com.atguigu.spring6.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Admin
 * @Create: 2024/7/17 - 上午10:35
 * @Version: v1.0
 * ClassName: springConfig
 * Package: com.atguigu.spring6.config
 * Description: 描述
 */
@Configuration  //  配置类
@ComponentScan("com.atguigu.spring6.resource")   //  开启组件扫描
public class springConfig {

}
