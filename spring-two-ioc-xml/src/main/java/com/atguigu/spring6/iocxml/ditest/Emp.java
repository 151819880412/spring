package com.atguigu.spring6.iocxml.ditest;

import java.util.Arrays;

/**
 * @Author: Admin
 * @Create: 2024/7/5 - 上午11:06
 * @Version: v1.0
 * ClassName: Emp
 * Package: com.atguigu.spring6.iocxml.ditest
 * Description: 员工类
 * 一个员工只能有一个部门
 */
public class Emp {
    private String name;
    private Integer age;
    private Dept dept;
    private String[] loves;

    public void work() {
        System.out.println(name + " " + age + " is working");
        dept.info();
        System.out.println(Arrays.toString(loves));
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

    public String[] getLoves() {
        return loves;
    }

    public void setLoves(String[] loves) {
        this.loves = loves;
    }

    public void setDeptName(String deptName) {
        if (this.dept != null) {
            this.dept.setDeptName(deptName);
        }
    }

}
