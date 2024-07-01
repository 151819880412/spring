#  1 Spring入门案例开发步骤
1. 引入 spring 相关依赖
    ```xml
    <dependencies>
        <!--spring context依赖-->
        <!--当你引入Spring Context依赖之后，表示将Spring的基础依赖引入了-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>6.0.2</version>
        </dependency>
    
        <!--junit5测试-->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.3.1</version>
        </dependency>
    </dependencies>
    ```
2. 创建类,定义属性和方法
    ```java
    package com.atguigu.spring6;
    
    public class User {
        public void sayHello(){
            System.out.println("helloworld");
        }
    }
    ```
3. 按照 spring 要求创建配置文件
   - 在resources目录创建一个 Spring 配置文件 beans.xml（配置文件名称可随意命名，如：springs.xm）
4. 在 spring 配置文件中配置相关信息
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    
        <!--
        属性：
            id：设置bean的唯一标识
            class：设置bean所对应类型的全类名
        -->
        <bean id="user" class="com.atguigu.spring6.User"></bean>
    
    </beans>
    ```
5. 进行最终测试
   ```java
   package com.atguigu.spring6;
   import org.junit.jupiter.api.Test;
   import org.springframework.context.support.ClassPathXmlApplicationContext;
   
   /**
    * @Author: Admin
    * @Create: 2024/7/1 - 下午3:35
    * @Version: v1.0
    * ClassName: TestUser
    * Package: com.atguigu.spring6
    * Description: 描述
    */
   public class TestUser {
   
       @Test
       public void testUser() {
           // 1. 加载 spring 配置对象, 创建 spring 容器对象
           ClassPathXmlApplicationContext contxt = new ClassPathXmlApplicationContext("bean.xml");
           // 2. 获取创建的对象
           User user = contxt.getBean("user", User.class);
           // 3. 使用对象调用方法进行测试
           System.out.println("user = " + user);
           user.sayHello();
            // 之前的写法
            User user = new User();
            user.sayHello();
       }
   }
   ```
![01-入门案例分析.png](01-入门案例分析.png)

# 2 启用Log4j2日志框架
   ```xml
   <!--log4j2的依赖-->
   <dependency>
       <groupId>org.apache.logging.log4j</groupId>
       <artifactId>log4j-core</artifactId>
       <version>2.19.0</version>
   </dependency>
   <dependency>
       <groupId>org.apache.logging.log4j</groupId>
       <artifactId>log4j-slf4j2-impl</artifactId>
       <version>2.19.0</version>
   </dependency>
   ```
- 在类的根路径下提供log4j2.xml配置文件（文件名固定为：log4j2.xml，文件必须放到类根路径下。）
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--日志级别以及优先级排序: OFF > FATAL > ERROR > WARN > INFO > DEBUG > TRACE > ALL -->
<!--Configuration后面的status，这个用于设置log4j2自身内部的信息输出，可以不设置，当设置成trace时，你会看到log4j2内部各种详细输出-->
<!--monitorInterval：Log4j能够自动检测修改配置 文件和重新配置本身，设置间隔秒数-->
<configuration status="WARN" monitorInterval="30">
    <Properties>
        <Property name="LOG_HOME">../logs/spring-first/</Property>
        <!--_TRACE_ID，业务自定义变量-->
        <property name="ALL_PATTERN">[%d{yyyy-MM-dd HH:mm:ss.SSS}][%t][%p]- %l - %m%n</property>
        <property name="CHARSET">UTF-8</property>
        <property name="FILE_SIZE">1GB</property>
        <property name="FILE_INDEX_MAX">30</property>
    </Properties>
    <!--先定义所有的appender-->
    <appenders>
        <!--这个输出控制台的配置-->
        <console name="Console" target="SYSTEM_OUT">
            <!--输出日志的格式-->
            <PatternLayout pattern="${ALL_PATTERN}"/>
        </console>
        <!--文件会打印出所有信息，这个log每次运行程序会自动清空，由append属性决定，这个也挺有用的，适合临时测试用-->
        <File name="log" fileName="${LOG_HOME}/spring-first.log" append="false">
            <PatternLayout pattern="${ALL_PATTERN}"/>
        </File>
        <!-- 这个会打印出所有的debug及以下级别的信息，每次大小超过size，则这size大小的日志会自动存入按年份-月份建立的文件夹下面并进行压缩，作为存档-->
        <RollingFile name="RollingFileDebug" fileName="${LOG_HOME}/debug.log"
                     filePattern="${LOG_HOME}/logs/$${date:yyyy-MM}/warn-%d{yyyy-MM-dd}-%i.log">
            <ThresholdFilter level="debug" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="${ALL_PATTERN}"/>
            <Policies>
                <TimeBasedTriggeringPolicy/>
                <SizeBasedTriggeringPolicy size="100 MB"/>
            </Policies>
            <!-- DefaultRolloverStrategy属性如不设置，则默认为最多同一文件夹下7个文件，这里设置了20 -->
            <DefaultRolloverStrategy max="20"/>
        </RollingFile>
        <RollingFile name="RollingFileInfo" fileName="${LOG_HOME}/info.log"
                     filePattern="${LOG_HOME}/logs/$${date:yyyy-MM}/info-%d{yyyy-MM-dd}-%i.log">
            <!--控制台只输出level及以上级别的信息（onMatch），其他的直接拒绝（onMismatch）-->
            <ThresholdFilter level="info" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="${ALL_PATTERN}"/>
            <Policies>
                <TimeBasedTriggeringPolicy/>
                <SizeBasedTriggeringPolicy size="100 MB"/>
            </Policies>
        </RollingFile>
        <RollingFile name="RollingFileError" fileName="${LOG_HOME}/error.log"
                     filePattern="${LOG_HOME}/logs/$${date:yyyy-MM}/error-%d{yyyy-MM-dd}-%i.log">
            <ThresholdFilter level="error" onMatch="ACCEPT" onMismatch="DENY"/>
            <PatternLayout pattern="${ALL_PATTERN}"/>
            <Policies>
                <TimeBasedTriggeringPolicy/>
                <SizeBasedTriggeringPolicy size="100 MB"/>
            </Policies>
        </RollingFile>
    </appenders>
    <!--然后定义logger，只有定义了logger并引入的appender，appender才会生效-->
    <loggers>
        <logger name="org.springframework" level="DEBUG"></logger>
        <root level="all">
            <appender-ref ref="Console"/>
            <appender-ref ref="RollingFileDebug"/>
            <appender-ref ref="RollingFileInfo"/>
            <appender-ref ref="RollingFileError"/>
        </root>
    </loggers>
</configuration>
```