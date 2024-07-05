package com.atguigu.spring6.iocxml.ditest;

/**
 * @Author: Admin
 * @Create: 2024/7/5 - 上午11:05
 * @Version: v1.0
 * ClassName: Dept
 * Package: com.atguigu.spring6.iocxml.ditest
 * Description: 部门类
 * 一个部门可以有多个员工
 */
public class Dept {
    private String deptName;

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void info() {
        System.out.println("部门名称：" + deptName);
    }

}
