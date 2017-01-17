package com.viyu.demo;

import com.viyu.aop.AopEngine;
import com.viyu.demo.testclass.TestClassNumberOne;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author Viyu
 * @desc 执行程序
 */
public class Demo {

    public static void main(String[] args) {

        //测试AopFramework对TestClassNumberOne的字节码的修改
        //这里只依赖AopFramework.jar
        File classFile = new File("./build/classes/main/com/viyu/demo/testclass/TestClassNumberOne.class");
        InputStream is;
        try {

            is = new FileInputStream(classFile);

            byte[] tBytes = AopEngine.doAspect("com.viyu.demo.testclass", is);
            is.close();
            FileOutputStream fout = new FileOutputStream(classFile);
            fout.write(tBytes);
            fout.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        //测试AopInvoker代码在TestClassNumberOne中的执行
        //这里只依赖AopInvoker.jar
        (new TestClassNumberOne()).doTestFunction1();
    }
}
