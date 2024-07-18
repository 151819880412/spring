package com.atguigu.spring6.aop.example;

/**
 * @Author: Admin
 * @Create: 2024/7/18 - 下午5:38
 * @Version: v1.0
 * ClassName: TestCalculator
 * Package: com.atguigu.spring6.aop.example
 * Description: 描述
 */
public class TestCalculator {
    public static void main(String[] args) {
        CalculatorImpl calculator = new CalculatorImpl();
        ProxyFactory proxyFactory = new ProxyFactory(calculator);
        Calculator proxy = (Calculator) proxyFactory.getProxy();
        proxy.add(1, 2);
    }
}
