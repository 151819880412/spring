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

# 3 IOC 容器
- IOC 容器是Spring框架的核心，它提供了对象实例化的功能，对象实例化后，对象之间的依赖关系由容器来维护。
- Spring 通过 IOC 容器来管理所有对象的实例化和初始化,控制对象与对象之间的依赖关系
- 我们将由 IOC 容器管理的对象称为 Spring Bean 它与使用关键字 new 创建的对象没有任何区别
- ![02-IoC容器.png](02-IoC%E5%AE%B9%E5%99%A8.png)

## 3.1 IOC 基于 XML 管理 Bean
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--User 对象创建-->
    <bean id="user" class="com.atguigu.spring6.iocxml.User"></bean>
</beans>
```
### 3.1.1 搭建子模块spring6-ioc-xml
### 3.1.2 实验一:获取bean
```java
package com.atguigu.spring6.iocxml;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Admin
 * @Create: 2024/7/4 - 上午10:03
 * @Version: v1.0
 * ClassName: TestUser
 * Package: com.atguigu.spring6.iocxml
 * Description: 描述
 */
public class TestUser {

   public static void main(String[] args){
       // <bean id="user" class="com.atguigu.spring6.iocxml.User"></bean>
       ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
       // 根据 id 获取 bean
       User user = context.getBean("user", User.class);
       System.out.println(user);

       // 根据类型获取 bean
       User user1 = context.getBean(User.class);
       System.out.println(user1);

       // 根据 id 和类型获取 bean
       User user2 = context.getBean("user", User.class);
       System.out.println(user2);
   }
}
```
```java
package com.atguigu.spring6.iocxml.bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: Admin
 * @Create: 2024/7/4 - 下午4:10
 * @Version: v1.0
 * ClassName: TestUserDao
 * Package: com.atguigu.spring6.iocxml.bean
 * Description: 描述
 */
public class TestUserDao {
    public static void main(String[] args) {
         // <!-- 2. 一个接口实现类获取过程-->
         // <bean id="userDao" class="com.atguigu.spring6.iocxml.bean.UserDaoImpl"></bean>
         // <bean id="personDao" class="com.atguigu.spring6.iocxml.bean.PersonDaoImpl"></bean>
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        // 根据接口获取接口对应的 bean
        // 要求只能有一个实现类,多个实现类会报错
        // UserDao userDao = context.getBean( UserDao.class);
        // System.out.println(userDao);

        // 根据 id 和类型获取 bean
        UserDao userDao = context.getBean("userDao", UserDao.class);
        UserDao personDao = context.getBean("personDao", UserDao.class);

        System.out.println(userDao);
        System.out.println(personDao);
    }
}
```
### 3.1.3 实验二:依赖注入之setter注入
- 创建类,定义属性,生成属性 set 方法
- 在 Spring 配置文件中配置
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

   <!--基于 set 方法注入-->
   <bean id="book" class="com.atguigu.spring6.iocxml.di.Book">
      <property name="name" value="java"/>
      <property name="author" value="zhangsan"/>
   </bean>
</beans>
```
```java
package com.atguigu.spring6.iocxml.di;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * @Author: Admin
 * @Create: 2024/7/5 - 上午10:03
 * @Version: v1.0
 * ClassName: TestDiBook
 * Package: com.atguigu.spring6.iocxml.di
 * Description: 描述
 */
public class TestDiBook {
    @Test
    public void testSet(){
        // 加载配置文件
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean-di.xml");
        // 根据 id 和类型获取 bean
        Book book = context.getBean("book", Book.class);
        System.out.println(book);

    }
}
```
### 3.1.4 实验三:依赖注入之构造器注入
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!--构造器和构造方法注入-->
    <bean id="bookCon" class="com.atguigu.spring6.iocxml.di.Book">
        <constructor-arg name="name" value="java"/>
        <constructor-arg name="author" value="zhangsan"/>
    </bean>
</beans>
```
```java
package com.atguigu.spring6.iocxml.di;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * @Author: Admin
 * @Create: 2024/7/5 - 上午10:03
 * @Version: v1.0
 * ClassName: TestDiBook
 * Package: com.atguigu.spring6.iocxml.di
 * Description: 描述
 */
public class TestDiBook {
    @Test
    public void testSet(){
        /*具体流程如下：
            Spring 根据配置文件中 <bean> 元素的 class 属性找到 Book 类。
            Spring 使用无参构造器实例化 Book 对象，这时会输出 "book无参构造器"。
            Spring 调用 setName 和 setAuthor 方法注入 name 和 author 属性。*/
        // 加载配置文件
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean-di.xml");
        // 根据 id 和类型获取 bean
        Book book = context.getBean("book", Book.class);
        System.out.println(book);

    }

    @Test
    public void testConstructor(){
        /*具体流程如下：
            Spring 根据配置文件中 <bean> 元素的 class 属性找到 Book 类。
            Spring 使用有参构造器实例化 Book 对象，这时会输出 "book有参构造器"。
            Spring 通过有参构造器注入 name 和 author 属性。*/
        // 加载配置文件
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean-di.xml");
        // 根据 id 和类型获取 bean
        Book book = context.getBean("bookCon", Book.class);
        System.out.println(book);

    }
}

```
### 3.1.5 实验四:特殊值处理
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!--基于 set 方法注入-->
    <bean id="book" class="com.atguigu.spring6.iocxml.di.Book">
        <property name="name" value="java"/>
        <property name="author" value="zhangsan"/>
        <!--        1. 空值处理-->
        <!--        <property name="others" >-->
        <!--        <null></null>-->
        <!--        </property>-->

        <!--        2. 空值处理-->
        <!--        <property name="others" value="&lt;&gt;" />-->

        <!--        3. CDATA 处理-->
        <!--        <property name="others" >-->
        <!--            <value>-->
        <!--                <![CDATA[a<b]]>-->
        <!--            </value>-->
        <!--        </property>-->
    </bean>

</beans>
```
### 3.1.6 实验五:为对象类型属性赋值
- 引用外部 bean
  - 创建 dept 和 emp 对象
    ```java
    package com.atguigu.spring6.iocxml.ditest;
    public class Dept {
        private String deptName;
    
        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }
    
        public void info() {
            System.out.println("部门名称：" + deptName);
        }
    }
    ```
    ```java
    package com.atguigu.spring6.iocxml.ditest;
    public class Emp {
        private String name;
        private Integer age;
        private Dept dept;
    
        public void work() {
            System.out.println(name + " " + age + " is working");
            dept.info();
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public void setAge(Integer age) {
            this.age = age;
        }
    
        public void setDept(Dept dept) {
            this.dept = dept;
        }
    }
    ```
  - 注入普通类型属性
  - 在 emp 的 bean 标签中引用 dept 的 bean 标签
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
        <!--
        方法1: 引入外部 bean
            1. 创建两个类对象: dept 和 emp
            2. 在 dept 和 emp 标签里面,使用 property 引入 dept 的 bean
        -->
        <bean id="dept" class="com.atguigu.spring6.iocxml.ditest.Dept">
            <property name="deptName" value="开发部"/>
        </bean>
        <bean id="emp" class="com.atguigu.spring6.iocxml.ditest.Emp">
            <!--普通属性注入 value -->
            <property name="age" value="18"/>
            <property name="name" value="张三"/>
            <!--对象类型属性注入 ref="bean的id" -->
            <property name="dept" ref="dept"/>
        </bean>
    </beans>
    ```
  - 引用内部 bean
      ```xml
      <?xml version="1.0" encoding="UTF-8"?>
      <beans xmlns="http://www.springframework.org/schema/beans"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
          <!--
          方法2:内部 bean
          -->
          <bean id="emp2" class="com.atguigu.spring6.iocxml.ditest.Emp">
              <!--普通属性注入 value -->
              <property name="age" value="44"/>
              <property name="name" value="李四"/>
              <!--内部 bean-->
              <property name="dept">
                  <bean id="dept2" class="com.atguigu.spring6.iocxml.ditest.Dept">
                      <property name="deptName" value="测试部"/>
                  </bean>
              </property>
          </bean>
      </beans>
      ```
      ```java
      package com.atguigu.spring6.iocxml.ditest;
      import org.springframework.context.support.ClassPathXmlApplicationContext;
        public class TestEmp {
        public static void main(String[] args) {
        // 加载配置文件
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean-ditest.xml");
        // 通过 id 和 class 获取 bean
        Emp emp = context.getBean("emp2", Emp.class);
        emp.work();
        }
      }
      ```
- 级联属性赋值
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
        <!--级联赋值-->
        <bean id="dept3" class="com.atguigu.spring6.iocxml.ditest.Dept">
            <property name="deptName" value="333部"/>
        </bean>
        <bean id="emp3" class="com.atguigu.spring6.iocxml.ditest.Emp">
            <property name="name" value="小明"/>
            <property name="age" value="33"/>
            <property name="dept" ref="dept3"></property>
            <property name="deptName" value="444部"></property>
        </bean>
    </beans>
    ```
```java
package com.atguigu.spring6.iocxml.ditest;
public class Emp {
    private String name;
    private Integer age;
    private Dept dept;

    public void work() {
        System.out.println(name + " " + age + " is working");
        dept.info();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    // 这里是关键
    public void setDeptName(String deptName) {
        if (this.dept != null) {
            this.dept.setDeptName(deptName);
        }
    }
}
```
### 3.1.7 实验六:为数组类型属性赋值
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!--注入数组类型的属性-->
    <bean id="dept" class="com.atguigu.spring6.iocxml.ditest.Dept">
        <property name="deptName" value="开发部"></property>
    </bean>
    <bean id="emp" class="com.atguigu.spring6.iocxml.ditest.Emp">
        <!--普通属性-->
        <property name="name" value="张三"/>
        <property name="age" value="18"/>
        <!--对象类型属性-->
        <property name="dept" ref="dept"/>
        <!--数组类型属性-->
        <property name="loves">
            <array>
                <value>football</value>
                <value>basketball</value>
                <value>swimming</value>
            </array>
        </property>
    </bean>
</beans>
```
### 3.1.8 实验七:为集合类型属性赋值
- 为 List 集合类型属性赋值
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="emp1" class="com.atguigu.spring6.iocxml.ditest.Emp">
        <property name="name" value="张三"/>
        <property name="age" value="18"/>
    </bean>
    <bean id="emp2" class="com.atguigu.spring6.iocxml.ditest.Emp">
        <property name="name" value="李四"/>
        <property name="age" value="20"/>
    </bean>
    <bean id="dept" class="com.atguigu.spring6.iocxml.ditest.Dept">
        <property name="deptName" value="开发部"/>
        <property name="empList">
            <list>
                <ref bean="emp1"/>
                <ref bean="emp2"/>
            </list>
        </property>
    </bean>
</beans>
```
- 为 map 类型属性赋值
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--
        1. 创建两个对象
        2. 注入普通类型属性
        3. 在学生 bean 注入 map 集合类型属性
    -->
    <bean id="teacher1" class="com.atguigu.spring6.iocxml.dimap.Teacher">
        <property name="id" value="1"/>
        <property name="name" value="张三"/>
    </bean>
    <bean id="teacher2" class="com.atguigu.spring6.iocxml.dimap.Teacher">
        <property name="id" value="2"/>
        <property name="name" value="小明"/>
    </bean>
    <bean id="student" class="com.atguigu.spring6.iocxml.dimap.Student">
        <property name="id" value="11"/>
        <property name="name" value="李四"/>
        <property name="teacherMap">
            <map>
                <entry key="a" value-ref="teacher1"/>
                <entry key="b" value-ref="teacher2"/>
            </map>
        </property>
    </bean>
</beans>
```
```java
package com.atguigu.spring6.iocxml.dimap;
import java.util.Map;

public class Student {
    private String name;
    private String id;
    private Map<String,Teacher> teacherMap;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Teacher> getTeacherMap() {
        return teacherMap;
    }

    public void setTeacherMap(Map<String, Teacher> teacherMap) {
        this.teacherMap = teacherMap;
    }

    public void run (){
        System.out.println("学生的id"+id+"  学生的name"+name);
        System.out.println(teacherMap);
    }
}
```
```java
package com.atguigu.spring6.iocxml.dimap;
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
```
```java
package com.atguigu.spring6.iocxml.dimap;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class TestStudent {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean-dimap.xml");
        Student student = context.getBean("student", Student.class);
        student.run();
    }
}
```
- 引用集合类型的 bean
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:util="http://www.springframework.org/schema/util"
       xsi:schemaLocation="http://www.springframework.org/schema/util
       http://www.springframework.org/schema/util/spring-util.xsd
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd"
>

    <!--
        1. 创建三个对象
        2. 注入普通类型属性
        3. 使用 util: 类型 定义
        4. 在学生 bean 引入 util: 定义 bean 完成 list,map 类型属性的注入
    -->
    <bean id="student" class="com.atguigu.spring6.iocxml.dimap.Student">
        <property name="name" value="学生"/>
        <property name="id" value="11"/>
        <!--注入 list,map 属性-->
        <property name="lessonList" ref="lessonList"></property>
        <property name="teacherMap" ref="teacherMap"></property>
    </bean>

    <util:list id="lessonList">
        <ref bean="lesson1"></ref>
        <ref bean="lesson2"></ref>
    </util:list>
    <util:map id="teacherMap">
        <!--下面代码会报错-->
        <entry>
            <key>
                <value>11111</value>
            </key>
            <ref bean="teacher1"></ref>
        </entry>
        <entry>
            <key>
                <value>222222</value>
            </key>
            <ref bean="teacher2"></ref>
        </entry>
    </util:map>

    <bean id="teacher1" class="com.atguigu.spring6.iocxml.dimap.Teacher">
        <property name="name" value="teacher1"/>
        <property name="id" value="1"/>
    </bean>
    <bean id="teacher2" class="com.atguigu.spring6.iocxml.dimap.Teacher">
        <property name="name" value="teacher2"/>
        <property name="id" value="2"/>
    </bean>
    <bean id="lesson1" class="com.atguigu.spring6.iocxml.dimap.Lesson">
        <property name="name" value="java"/>
    </bean>
    <bean id="lesson2" class="com.atguigu.spring6.iocxml.dimap.Lesson">
        <property name="name" value="spring"/>
    </bean>
</beans>
```
```java
package com.atguigu.spring6.iocxml.dimap;
import java.util.List;
import java.util.Map;

public class Student {
    private String name;
    private String id;
    private Map<String,Teacher> teacherMap;
    private List<Lesson> lessonList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Teacher> getTeacherMap() {
        return teacherMap;
    }

    public void setTeacherMap(Map<String, Teacher> teacherMap) {
        this.teacherMap = teacherMap;
    }

    public List<Lesson> getLessonList() {
        return lessonList;
    }

    public void setLessonList(List<Lesson> lessonList) {
        this.lessonList = lessonList;
    }

    public void run (){
        System.out.println("学生的id"+id+"  学生的name"+name);
        System.out.println(teacherMap);
        System.out.println(lessonList);
    }
}
```
```java
package com.atguigu.spring6.iocxml.dimap;
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
```
```java
package com.atguigu.spring6.iocxml.dimap;
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
```
### 3.1.9 实验八:p命名空间
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:util="http://www.springframework.org/schema/util"
       xmlns:p="http://www.springframework.org/schema/p"
       xsi:schemaLocation="http://www.springframework.org/schema/util
       http://www.springframework.org/schema/util/spring-util.xsd
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd"
>
    <!--P命名空间注入-->
    <bean id="studentp" class="com.atguigu.spring6.iocxml.dimap.Student"
          p:id="1pp" p:name="张三pp"
          p:lessonList-ref="lessonList"
          p:teacherMap-ref="teacherMap"
    />
</beans>
```
### 3.1.10 实验九:引入外部属性文件
```properties
jdbc.user=root
jdbc.password=qwert123
jdbc.url=jdbc:mysql://localhost:3306/atguigu?serverTimezone=UTC
jdbc.driver=com.mysql.cj.jdbc.Driver
```
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
">
    <!--引入外部属性文件-->
    <!--<context:property-placeholder location="jdbc.properties"/>-->
    <context:property-placeholder location="classpath:jdbc.properties"/>
    <!--完成数据库数据的注入-->
    <bean id="druidDataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.user}"/>
        <property name="password" value="${jdbc.password}"/>
        <property name="driverClassName" value="${jdbc.driver}"/>
    </bean>
</beans>
```
```java
package com.atguigu.spring6.iocxml.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.sql.Connection;
import java.sql.SQLException;

public class TestJdbc {

    @Test
    public void test() throws SQLException {
        // 方法一
        // DruidDataSource druidDataSource = new DruidDataSource();
        // druidDataSource.setUrl("jdbc:mysql://localhost:3306/atguigu?serverTimezone=UTC");
        // druidDataSource.setUsername("root");
        // druidDataSource.setPassword("qwert123");
        // druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        // System.out.println(druidDataSource.getConnection());

        // 方法二
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean-jdbc.xml");
        DruidDataSource druidDataSource = context.getBean(DruidDataSource.class);
        System.out.println(druidDataSource.getConnection());
    }
}
```
### 3.1.11 实验十:bean的作用域
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!--
    通过 scope 可以配置单实例还是多实例  默认单实例
    单实例是初始化的时候创建  多实例是 getBean 的时候创建
    singleton  单实例
    prototype  多实例
    -->
    <bean id="orders" class="com.atguigu.spring6.iocxml.scope.Orders" scope="singleton">
    </bean>
</beans>
```
```java
package com.atguigu.spring6.iocxml.scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestOrders {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean-scope.xml");
        Orders orders1 = context.getBean("orders", Orders.class);
        Orders orders2 = context.getBean("orders", Orders.class);
        System.out.println(orders1);
        System.out.println(orders2);

    }
}
```
### 3.1.12 实验十-:bean生命周期
- bean 对象创建(调用无参构造)
- 给 bean 对象设置相关属性
- bean 后置处理器(初始化之前执行)
- bean 初始化(调用指定初始化的方法)
- bean 后置处理器(初始化之后执行)
- bean 对象创建完成了，可以使用
- bean 对象销毁(指定销毁的方法)
- IOC 容器关闭
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="user" class="com.atguigu.spring6.iocxml.life.User" scope="singleton" init-method="init" destroy-method="destroy">
        <property name="name" value="张三"/>
    </bean>

    <bean id="myBeanPost" class="com.atguigu.spring6.iocxml.life.MyBeanPost"/>
</beans>
```
```java
package com.atguigu.spring6.iocxml.life;
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
```
```java
package com.atguigu.spring6.iocxml.life;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;

public class MyBeanPost implements BeanPostProcessor {
    @Override
    @Nullable
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("3. bean 后置处理器(初始化之前执行)");
        return bean;
    }

    @Override
    @Nullable
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("5. bean 后置处理器(初始化之后执行)");
        return bean;
    }
}
```
```java
package com.atguigu.spring6.iocxml.life;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestUser {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean-life.xml");
        User user = context.getBean("user", User.class);
        System.out.println("6. bean 对象创建完成了，可以使用");
        System.out.println(user);

        context.close();

    }
}
```
### 3.1.13 实验十二:FactoryBean
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="user" class="com.atguigu.spring6.iocxml.factoryBean.MyFactoryBean">

    </bean>
</beans>
```
```java
package com.atguigu.spring6.iocxml.factoryBean;
import org.springframework.beans.factory.FactoryBean;

public class MyFactoryBean implements FactoryBean<User> {
    @Override
    public User getObject() throws Exception {
        return new User();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}
// public class MyFactoryBean  {
//
// }
```
```java
package com.atguigu.spring6.iocxml.factoryBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestUser {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean-factorybean.xml");
        User user = context.getBean("user", User.class);
        // MyFactoryBean user = context.getBean("user", MyFactoryBean.class);
        System.out.println(user);
    }
}

```
### 3.1.14 实验十三:基于xml自动装配
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    <!--根据类型自动装配
        注意: 如果根据类型自动装配,则必须保证容器中唯一 id 必须和类名可以不一致
        如果根据名称自动装配,则容器中可以有多个
        autowire="byType" 根据类型自动装配
        autowire="byName" 根据名称自动装配
    -->
    <bean id="userController" class="com.atguigu.spring6.iocxml.auto.controller.UserController" autowire="byType"/>
    <bean id="userServices" class="com.atguigu.spring6.iocxml.auto.service.UserServiceImpl" autowire="byType"/>
    <bean id="userDao" class="com.atguigu.spring6.iocxml.auto.dao.UserDaoImpl" autowire="byType"/>
    <!--根据名称自动装配
        注意: 如果根据名称自动装配,则容器中可以有多个,但是 id 必须和类名一致
    -->
    <!--    <bean id="userController" class="com.atguigu.spring6.iocxml.auto.controller.UserController" autowire="byName"/>-->
    <!--    <bean id="userService" class="com.atguigu.spring6.iocxml.auto.service.UserServiceImpl" autowire="byName"/>-->
    <!--    <bean id="userDao" class="com.atguigu.spring6.iocxml.auto.dao.UserDaoImpl" autowire="byName"/>-->
</beans>
```
```java
package com.atguigu.spring6.iocxml.auto.controller;
import com.atguigu.spring6.iocxml.auto.service.UserService;
import com.atguigu.spring6.iocxml.auto.service.UserServiceImpl;
import org.junit.jupiter.api.Test;

public class UserController {

    public UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void addUserController(){
        System.out.println("Controller层调用了");
        // 调用 service 的方法
        userService.addUserService();

        // UserServiceImpl userService = new UserServiceImpl();
        // userService.addUserService();
    }
}
```
```java
package com.atguigu.spring6.iocxml.auto.service;
import com.atguigu.spring6.iocxml.auto.dao.UserDao;

public class UserServiceImpl implements UserService{

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUserService() {
        System.out.println("UserService---");
        // 调用 dao 中的方法
        userDao.addUserDao();
        // UserDaoImpl userDao = new UserDaoImpl();
        // userDao.addUserDao();
    }
}
```
```java
package com.atguigu.spring6.iocxml.auto.service;

public interface UserService {
    public void addUserService();
}
```
```java
package com.atguigu.spring6.iocxml.auto.dao;

public class UserDaoImpl implements UserDao{
    @Override
    public void addUserDao() {
        System.out.println("addUserDao---");
    }
}
```
```java
package com.atguigu.spring6.iocxml.auto.dao;

public interface UserDao {
    public void addUserDao();
}
```
```java
package com.atguigu.spring6.iocxml.auto;
import com.atguigu.spring6.iocxml.auto.controller.UserController;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestUser {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean-auto.xml");
        UserController userController = context.getBean("userController", UserController.class);
        userController.addUserController();
    }
}

```
### 3.1.1 基于注解的IOC
从 Java 5 开始，Java 增加了对注解（Annotation）的支持，它是代码中的一种特殊标记，可以在编译、类加载和运行时被读取，执行相应的处理。开发人员可以通过注解在不改变原有代码和逻辑的情况下，在源代码中嵌入补充信息。
Spring 从 2.5 版本开始提供了对注解技术的全面支持，我们可以使用注解来实现自动装配，简化 Spring 的 XML 配置。
Spring 通过注解实现自动装配的步骤如下：
### 3.1.2 引入依赖
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.atguigu</groupId>
        <artifactId>spring6</artifactId>
        <version>1.0-SNAPSHOT</version>
    </parent>

    <artifactId>spring6-ioc-annotation</artifactId>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <!--spring context依赖-->
        <!--当你引入Spring Context依赖之后，表示将Spring的基础依赖引入了-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>6.0.3</version>
        </dependency>

        <!--junit5测试-->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.3.1</version>
        </dependency>

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
    </dependencies>
</project>
```
### 3.1.3 开启组件扫描
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
    http://www.springframework.org/schema/context
            http://www.springframework.org/schema/context/spring-context.xsd">
    <!--情况一：最基本的扫描方式-->
    <context:component-scan base-package="com.atguigu.spring6"/>
    <!--    &lt;!&ndash;情况二：指定要排除的组件&ndash;&gt;-->
    <!--    <context:component-scan base-package="com.atguigu.spring6">-->
    <!--        &lt;!&ndash; context:exclude-filter标签：指定排除规则 &ndash;&gt;-->
    <!--        &lt;!&ndash;-->
    <!--            type：设置排除或包含的依据-->
    <!--            type="annotation"，根据注解排除，expression中设置要排除的注解的全类名-->
    <!--            type="assignable"，根据类型排除，expression中设置要排除的类型的全类名-->
    <!--        &ndash;&gt;-->
    <!--        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>-->
    <!--        &lt;!&ndash;<context:exclude-filter type="assignable" expression="com.atguigu.spring6.controller.UserController"/>&ndash;&gt;-->
    <!--    </context:component-scan>-->
    <!--    &lt;!&ndash;情况三：仅扫描指定组件&ndash;&gt;-->
    <!--    <context:component-scan base-package="com.atguigu" use-default-filters="false">-->
    <!--        &lt;!&ndash; context:include-filter标签：指定在原有扫描规则的基础上追加的规则 &ndash;&gt;-->
    <!--        &lt;!&ndash; use-default-filters属性：取值false表示关闭默认扫描规则 &ndash;&gt;-->
    <!--        &lt;!&ndash; 此时必须设置use-default-filters="false"，因为默认规则即扫描指定包下所有类 &ndash;&gt;-->
    <!--        &lt;!&ndash;-->
    <!--            type：设置排除或包含的依据-->
    <!--            type="annotation"，根据注解排除，expression中设置要排除的注解的全类名-->
    <!--            type="assignable"，根据类型排除，expression中设置要排除的类型的全类名-->
    <!--        &ndash;&gt;-->
    <!--        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>-->
    <!--        &lt;!&ndash;<context:include-filter type="assignable" expression="com.atguigu.spring6.controller.UserController"/>&ndash;&gt;-->
    <!--    </context:component-scan>-->
</beans>
```
### 3.1.4  使用注解定义 Bean
Spring 提供了以下多个注解，这些注解可以直接标注在 Java 类上，将它们定义成 Spring Bean。

| 注解        | 说明                                                         |
| ----------- | ------------------------------------------------------------ |
| @Component  | 该注解用于描述 Spring 中的 Bean，它是一个泛化的概念，仅仅表示容器中的一个组件（Bean），并且可以作用在应用的任何层次，例如 Service 层、Dao 层等。  使用时只需将该注解标注在相应类上即可。 |
| @Repository | 该注解用于将数据访问层（Dao 层）的类标识为 Spring 中的 Bean，其功能与 @Component 相同。 |
| @Service    | 该注解通常作用在业务层（Service 层），用于将业务层的类标识为 Spring 中的 Bean，其功能与 @Component 相同。 |
| @Controller | 该注解通常作用在控制层（如SpringMVC 的 Controller），用于将控制层的类标识为 Spring 中的 Bean，其功能与 @Component 相同。 |

### 3.1.5 依赖注入
#### 实验一：@Autowired注入
- 场景一：属性注入
```java
package com.atguigu.spring6.autowired.controller;
import com.atguigu.spring6.autowired.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    //  注入 Service
    // 第一种方式:属性注入
    @Autowired  //  根据类型找到对应的对象并注入
    private UserService userService;
    public void addUserController()
    {
        System.out.println("Controller 调用 Service");
        userService.addUserService();
    }
}
```
```java
package com.atguigu.spring6.autowired.service;

public interface UserService {
    public void addUserService();
}
```
```java
package com.atguigu.spring6.autowired.service;
import com.atguigu.spring6.autowired.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    // 注入 dao
    // 第一种方式:属性注入
    @Autowired  //  根据类型找到对应的对象并注入
    private UserDao userDao;

    @Override
    public void addUserService() {
        System.out.println("Service 调用 Dao");
        userDao.addUserDao();
    }
}
```
```java
package com.atguigu.spring6.autowired.dao;

public interface UserDao {
    public void addUserDao();
}
```
```java
package com.atguigu.spring6.autowired.dao;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao{

    @Override
    public void addUserDao() {
        System.out.println("dao层添加用户");
    }
}

```
```java
package com.atguigu.spring6.autowired;
import com.atguigu.spring6.autowired.controller.UserController;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestUserController {

    public static void main(String[] args)
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        UserController userController = context.getBean("userController", UserController.class);
        userController.addUserController();
    }
}
```
- 场景二：set 注入
```java
package com.atguigu.spring6.autowired.controller;
import com.atguigu.spring6.autowired.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    //  注入 Service
    // 第一种方式:属性注入
    // @Autowired  //  根据类型找到对应的对象并注入
    // private UserService userService;

    // 第二种方式: set 注入
    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void addUserController()
    {
        System.out.println("Controller 调用 Service");
        userService.addUserService();
    }
}

```
```java
package com.atguigu.spring6.autowired.service;
import com.atguigu.spring6.autowired.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    // 注入 dao
    // 第一种方式:属性注入
    // @Autowired  //  根据类型找到对应的对象并注入
    // private UserDao userDao;

    // 第二种方式: set 注入
    private UserDao userDao;
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUserService() {
        System.out.println("Service 调用 Dao");
        userDao.addUserDao();
    }
}
```
- 场景三：构造方法注入
```java
package com.atguigu.spring6.autowired.controller;
import com.atguigu.spring6.autowired.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    //  注入 Service
    // 第一种方式:属性注入
    // @Autowired  //  根据类型找到对应的对象并注入
    // private UserService userService;

    // 第二种方式: set 注入
    // private UserService userService;
    // @Autowired
    // public void setUserService(UserService userService) {
    //     this.userService = userService;
    // }

    //  场景三：构造方法注入
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void addUserController() {
        System.out.println("Controller 调用 Service");
        userService.addUserService();
    }
}
```
```java
package com.atguigu.spring6.autowired.service;
import com.atguigu.spring6.autowired.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    // 注入 dao
    // 第一种方式:属性注入
    // @Autowired  //  根据类型找到对应的对象并注入
    // private UserDao userDao;

    // 第二种方式: set 注入
    // private UserDao userDao;
    // @Autowired
    // public void setUserDao(UserDao userDao) {
    //     this.userDao = userDao;
    // }

    //  场景三：构造方法注入
    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUserService() {
        System.out.println("Service 调用 Dao");
        userDao.addUserDao();
    }
}
```
- 场景四：形参上注入
```java
package com.atguigu.spring6.autowired.controller;

import com.atguigu.spring6.autowired.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    // 场景四：形参上注入
    private UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    public void addUserController() {
        System.out.println("Controller 调用 Service");
        userService.addUserService();
    }
}
```
```java
package com.atguigu.spring6.autowired.service;
import com.atguigu.spring6.autowired.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
   
    // 场景四：形参上注入
    private UserDao userDao;

    public UserServiceImpl(@Autowired UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUserService() {
        System.out.println("Service 调用 Dao");
        userDao.addUserDao();
    }
}
```
- 场景五：只且只有一个有参数构造函数,可以省略注解
```java
package com.atguigu.spring6.autowired.controller;

import com.atguigu.spring6.autowired.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    //  场景五：只且只有一个有参数构造函数,可以省略注解
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void addUserController() {
        System.out.println("Controller 调用 Service");
        userService.addUserService();
    }
}

```
```java
package com.atguigu.spring6.autowired.service;
import com.atguigu.spring6.autowired.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    //  场景五：只且只有一个有参数构造函数,可以省略注解
    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUserService() {
        System.out.println("Service 调用 Dao");
        userDao.addUserDao();
    }
}
```
- 场景六：@Autowired注解和@Qualifier注解联合
```java
package com.atguigu.spring6.autowired.service;
import com.atguigu.spring6.autowired.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    // 场景四：形参上注入
    // 会报错 因为有多个实现类
    // private UserDao userDao;
    //
    // public UserServiceImpl(@Autowired UserDao userDao) {
    //     this.userDao = userDao;
    // }

    // 场景六：@Autowired注解和@Qualifier注解联合
    @Autowired
    @Qualifier(value = "userRedisDaoImpl")
    private UserDao userDao;

    public void UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUserService() {
        System.out.println("Service 调用 Dao");
        userDao.addUserDao();
    }
}
```
```java
package com.atguigu.spring6.autowired.dao;
import org.springframework.stereotype.Repository;

@Repository
public class UserRedisDaoImpl implements UserDao{
    @Override
    public void addUserDao() {
        System.out.println("dao redis .......");
    }
}
```
#### 实验二：@Resource注入
@Resource注解也可以完成属性注入。那它和@Autowired注解有什么区别？

- @Resource注解是JDK扩展包中的，也就是说属于JDK的一部分。所以该注解是标准注解，更加具有通用性。(JSR-250标准中制定的注解类型。JSR是Java规范提案。)
- @Autowired注解是Spring框架自己的。
- **@Resource注解默认根据名称装配byName，未指定name时，使用属性名作为name。通过name找不到的话会自动启动通过类型byType装配。**
- **@Autowired注解默认根据类型装配byType，如果想根据名称装配，需要配合@Qualifier注解一起用。**
- @Resource注解用在属性上、setter方法上。
- @Autowired注解用在属性上、setter方法上、构造方法上、构造方法参数上。

@Resource注解属于JDK扩展包，所以不在JDK当中，需要额外引入以下依赖：【**如果是JDK8的话不需要额外引入依赖。高于JDK11或低于JDK8需要引入以下依赖。**】
```xml
<dependency>
    <groupId>jakarta.annotation</groupId>
    <artifactId>jakarta.annotation-api</artifactId>
    <version>2.1.1</version>
</dependency>
```
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
    http://www.springframework.org/schema/context
            http://www.springframework.org/schema/context/spring-context.xsd">
    <!--情况一：最基本的扫描方式-->
    <context:component-scan base-package="com.atguigu.spring6.resource"/>
    <!--    &lt;!&ndash;情况二：指定要排除的组件&ndash;&gt;-->
    <!--    <context:component-scan base-package="com.atguigu.spring6">-->
    <!--        &lt;!&ndash; context:exclude-filter标签：指定排除规则 &ndash;&gt;-->
    <!--        &lt;!&ndash;-->
    <!--            type：设置排除或包含的依据-->
    <!--            type="annotation"，根据注解排除，expression中设置要排除的注解的全类名-->
    <!--            type="assignable"，根据类型排除，expression中设置要排除的类型的全类名-->
    <!--        &ndash;&gt;-->
    <!--        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>-->
    <!--        &lt;!&ndash;<context:exclude-filter type="assignable" expression="com.atguigu.spring6.controller.UserController"/>&ndash;&gt;-->
    <!--    </context:component-scan>-->
    <!--    &lt;!&ndash;情况三：仅扫描指定组件&ndash;&gt;-->
    <!--    <context:component-scan base-package="com.atguigu" use-default-filters="false">-->
    <!--        &lt;!&ndash; context:include-filter标签：指定在原有扫描规则的基础上追加的规则 &ndash;&gt;-->
    <!--        &lt;!&ndash; use-default-filters属性：取值false表示关闭默认扫描规则 &ndash;&gt;-->
    <!--        &lt;!&ndash; 此时必须设置use-default-filters="false"，因为默认规则即扫描指定包下所有类 &ndash;&gt;-->
    <!--        &lt;!&ndash;-->
    <!--            type：设置排除或包含的依据-->
    <!--            type="annotation"，根据注解排除，expression中设置要排除的注解的全类名-->
    <!--            type="assignable"，根据类型排除，expression中设置要排除的类型的全类名-->
    <!--        &ndash;&gt;-->
    <!--        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>-->
    <!--        &lt;!&ndash;<context:include-filter type="assignable" expression="com.atguigu.spring6.controller.UserController"/>&ndash;&gt;-->
    <!--    </context:component-scan>-->
</beans>
```
```java
package com.atguigu.spring6.resource.controller;
import com.atguigu.spring6.resource.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;


@Controller
public class UserController {

    // 根据名称进行注入
    @Resource(name = "myUserService")
    private UserService userService;

    public void addUserController()
    {
        System.out.println("Controller 调用 Service");
        userService.addUserService();
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        UserController userController = context.getBean("userController", UserController.class);
        userController.addUserController();

    }
}
```
```java
package com.atguigu.spring6.resource.service;
import com.atguigu.spring6.resource.dao.UserDao;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("myUserService")
public class UserServiceImpl implements UserService {

    // 不指定名称就根据属性名称进行注入
    @Resource
    UserDao myUserDao;

    @Override
    public void addUserService() {
        System.out.println("Service 调用 Dao");
        myUserDao.addUserDao();
    }
}
```
```java
package com.atguigu.spring6.resource.dao;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    @Override
    public void addUserDao() {
        System.out.println("dao层添加用户");
    }
}
```

#### spring 全注解开发
```java
package com.atguigu.spring6.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
@Configuration  //  配置类
@ComponentScan("com.atguigu.spring6.resource")   //  开启组件扫描
public class springConfig {

}
```
```java
package com.atguigu.spring6.resource;

import com.atguigu.spring6.config.springConfig;
import com.atguigu.spring6.resource.controller.UserController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestUserControllerAnno {
    public static void main(String[] args) {
        //  加载配置类
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(springConfig.class);
        UserController userController = annotationConfigApplicationContext.getBean("userController", UserController.class);
        userController.addUserController();

    }
}
```
# 4. 原理-手写 IOC
## 4.1 回顾 JAVA 反射
`Java`反射机制是在运行状态中，对于任意一个类，都能够知道这个类的所有属性和方法；对于任意一个对象，都能够调用它的任意方法和属性；这种动态获取信息以及动态调用对象方法的功能称为`Java`语言的反射机制。简单来说，反射机制指的是程序在运行时能够获取自身的信息。

要想解剖一个类，必须先要**获取到该类的Class对象**。而剖析一个类或用反射解决具体的问题就是使用相关API**（1）java.lang.Class（2）java.lang.reflect**，所以，**Class对象是反射的根源**。
```java
package com.atguigu.reflect;

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
```
```java
package com.atguigu.reflect;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
```
## 4.2 实现 Spring 的 IOC
![img.png](4.2%20实现%20Spring%20的%20IOC.png)

搭建模块：guigu-spring，搭建方式如其他spring子模块
```java
package com.atguigu.service;

public interface UserService {
    public void addUserService();
}
```
```java
package com.atguigu.service.impl;

import com.atguigu.anno.Bean;
import com.atguigu.anno.Di;
import com.atguigu.dao.UserDao;
import com.atguigu.service.UserService;

@Bean
public class UserServiceImpl implements UserService {
    @Di
    private UserDao userDao;

    @Override
    public void addUserService() {
        System.out.println("service........");
        userDao.addUserDao();
    }
}
```
```java
package com.atguigu.dao;

public interface UserDao {
    public void addUserDao();
}

```
```java
package com.atguigu.dao.impl;
import com.atguigu.anno.Bean;
import com.atguigu.dao.UserDao;

@Bean
public class UserDaoImpl implements UserDao {
    @Override
    public void addUserDao() {
        System.out.println("dao........");
    }
}
```
```java
package com.atguigu.anno;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bean {
}
```
```java
package com.atguigu.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Di {
}
```
```java
package com.atguigu.bean;

public interface ApplicationContext {
    Object getBean(Class clazz);
}
```
```java
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

```
```java
package com.atguigu;
import com.atguigu.bean.AnnotationApplicationContext;
import com.atguigu.service.UserService;
public class TestBean {
    public static void main(String[] args) {
            AnnotationApplicationContext context = new AnnotationApplicationContext("com.atguigu");
            UserService userService = (UserService)context.getBean(UserService.class);
            userService.addUserService();
    }
}
```

# 5. 面向切面 : AOP
## 5.1 场景模拟
```java
package com.atguigu.spring6.aop.example;

public interface Calculator {
    int add(int i, int j);
    int sub(int i, int j);
    int mul(int i, int j);
    int div(int i, int j);
}
```
```java
package com.atguigu.spring6.aop.example;

public class CalculatorImpl implements Calculator {

    @Override
    public int add(int i, int j) {
        int result = i + j;
        System.out.println("方法内部 result = " + result);
        return result;
    }

    @Override
    public int sub(int i, int j) {
        int result = i - j;
        System.out.println("方法内部 result = " + result);
        return result;
    }

    @Override
    public int mul(int i, int j) {
        int result = i * j;
        System.out.println("方法内部 result = " + result);
        return result;
    }

    @Override
    public int div(int i, int j) {
        int result = i / j;
        System.out.println("方法内部 result = " + result);
        return result;
    }
}
```
```java
package com.atguigu.spring6.aop.example;

public class CalculatorLogImpl implements Calculator {

    @Override
    public int add(int i, int j) {
        System.out.println("[日志] add 方法开始了，参数是：" + i + "," + j);
        int result = i + j;
        System.out.println("方法内部 result = " + result);
        System.out.println("[日志] add 方法结束了，结果是：" + result);
        return result;
    }

    @Override
    public int sub(int i, int j) {
        System.out.println("[日志] sub 方法开始了，参数是：" + i + "," + j);
        int result = i - j;
        System.out.println("方法内部 result = " + result);
        System.out.println("[日志] sub 方法结束了，结果是：" + result);
        return result;
    }

    @Override
    public int mul(int i, int j) {
        System.out.println("[日志] mul 方法开始了，参数是：" + i + "," + j);
        int result = i * j;
        System.out.println("方法内部 result = " + result);
        System.out.println("[日志] mul 方法结束了，结果是：" + result);
        return result;
    }

    @Override
    public int div(int i, int j) {
        System.out.println("[日志] div 方法开始了，参数是：" + i + "," + j);
        int result = i / j;
        System.out.println("方法内部 result = " + result);
        System.out.println("[日志] div 方法结束了，结果是：" + result);
        return result;
    }
}
```
## 5.2 代理模式
```java
package com.atguigu.spring6.aop.example;

/**
 * @Author: Admin
 * @Create: 2024/7/18 - 上午11:07
 * @Version: v1.0
 * ClassName: CalculatorStaticProxy
 * Package: com.atguigu.spring6.aop.example
 * Description: 静态代理
 */
public class CalculatorStaticProxy implements Calculator {
    // 被代理目标对象传递进来
    private Calculator calculator;

    public CalculatorStaticProxy(Calculator calculator) {
        this.calculator = calculator;
    }


    @Override
    public int add(int i, int j) {
        // 输出日志
        System.out.println("[日志] add 方法开始了，参数是：" + i + "," + j);
        int result = calculator.add(i, j);
        System.out.println("[日志] add 方法结束了，结果是：" + result);
        return result;
    }

    @Override
    public int sub(int i, int j) {
        return 0;
    }

    @Override
    public int mul(int i, int j) {
        return 0;
    }

    @Override
    public int div(int i, int j) {
        return 0;
    }
}
```
```java
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
```
```java
package com.atguigu.spring6.aop.example;

public class TestCalculator {
    public static void main(String[] args) {
        CalculatorImpl calculator = new CalculatorImpl();
        ProxyFactory proxyFactory = new ProxyFactory(calculator);
        Calculator proxy = (Calculator) proxyFactory.getProxy();
        proxy.add(1, 2);
    }
}
```

## 5.3 AOP 概念以及相关术语
## 5.4 基于注解的AOP
## 5.5基于XML的AOP
# 6. 但愿色素: JUnit
# 7. 事务
# 8. 资源操作: Resources
# 9. 国际化: i18n
# 10. 数据校验: Validation
# 11. 提前编译: AOT