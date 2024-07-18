package com.atguigu.spring6.aop.example;

/**
 * @Author: Admin
 * @Create: 2024/7/18 - 上午10:53
 * @Version: v1.0
 * ClassName: CalculatorImpl
 * Package: com.atguigu.spring6.aop.example
 * Description: 描述
 */
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