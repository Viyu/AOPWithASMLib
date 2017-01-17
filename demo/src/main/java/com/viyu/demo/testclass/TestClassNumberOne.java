package com.viyu.demo.testclass;

/**
 * @author Viyu
 * @desc 测试类
 */
public class TestClassNumberOne {


    public void doTestFunction1() {
        System.out.println("do test function....");
    }

    public void doTestFunction2() {
        try {
            System.out.println("do test function 2....");
            Thread.sleep(1100);
        } catch (InterruptedException ex) {
        }
    }
}
