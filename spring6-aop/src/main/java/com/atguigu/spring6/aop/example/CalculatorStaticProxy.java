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