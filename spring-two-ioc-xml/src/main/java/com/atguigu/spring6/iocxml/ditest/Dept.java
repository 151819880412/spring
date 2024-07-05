package com.atguigu.spring6.iocxml.ditest;

import java.util.List;

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
    private List<Emp> empList;

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void info() {
        System.out.println("部门名称：" + deptName);
        for (Emp emp : empList) {
            System.out.println(emp.getName() + " " + emp.getAge());
        }
    }

    public List<Emp> getEmpList() {
        return empList;
    }

    public void setEmpList(List<Emp> empList) {
        this.empList = empList;
    }
}
