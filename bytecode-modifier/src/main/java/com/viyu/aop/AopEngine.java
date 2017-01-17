package com.viyu.aop;


import com.viyu.aop.asm.AopClassVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.objectweb.asm.ClassReader.EXPAND_FRAMES;
import static org.objectweb.asm.ClassWriter.COMPUTE_MAXS;
import static org.objectweb.asm.Opcodes.ASM5;


/**
 * @author viyu
 * @desc AOP执行的调用入口
 */
public class AopEngine {

    /**
     * 调用aop
     *
     * @param targetPackageName 目标包名，要被修改的类的包名
     * @param classInputStream  类的输入流
     * @return
     * @throws Exception
     */
    public static byte[] doAspect(String targetPackageName, InputStream classInputStream) throws Exception {
        return doAspect(targetPackageName, new ClassReader(classInputStream));
    }

    /**
     * 调用aop
     *
     * @param targetPackageName 目标包名，要被修改的类的包名
     * @param classBytes        类的二进制数据
     * @return
     * @throws Exception
     */
    public static byte[] doAspect(String targetPackageName, byte[] classBytes) throws Exception {
        return doAspect(targetPackageName, new ClassReader(classBytes));
    }


    /**
     * 调用aop
     *
     * @param targetPackageName 目标包名，要被修改的类的包名
     * @param classFile         类的二进制文件
     * @return
     */
    public static byte[] doAspect(String targetPackageName, File classFile) {
        InputStream is = null;
        byte[] tBytes = null;
        try {
            is = new FileInputStream(classFile);
            tBytes = doAspect(targetPackageName, is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {

            }
        }
        return tBytes;
    }

    private static byte[] doAspect(String targetPackageName, ClassReader classReader) {
        ClassWriter tClassWrite = new ClassWriter(COMPUTE_MAXS);
        AopClassVisitor tAopClassVisitor = new AopClassVisitor(targetPackageName, ASM5, tClassWrite);
        classReader.accept(tAopClassVisitor, EXPAND_FRAMES);
        byte[] tAspectedClassByte = tClassWrite.toByteArray();
        return tAspectedClassByte;
    }
}
