package com.viyu.aop.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

/**
 * @author Viyu	
 * @desc AOP class visitor，主要做的就是调用了AopMethodVisitor
 */
public class AopClassVisitor extends ClassVisitor {

    /**
     * 目标包名，就是要被修改的类的包名
     */
    private final String mTargetPackageName;

    /**
     * 实际的类全名
     */
    private String mActualClassFullName;

    /**
     * 是否需要修改，如果mActualClassFullName命中mTargetPackageName就需要修改
     */
    private boolean mNeedModifyMethod = false;

    public AopClassVisitor(String targetPackageName, int api, ClassVisitor cv) {
        super(api, cv);
        mTargetPackageName = targetPackageName;
        mNeedModifyMethod = false;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        mActualClassFullName = name.replaceAll("/", ".");
        if (mActualClassFullName != null) {
            mNeedModifyMethod = mActualClassFullName.startsWith(mTargetPackageName);
        }
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (mNeedModifyMethod) {
            return new AopMethodVisitor(api, mv, access, desc, mActualClassFullName, name);
        }
        return mv;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        mNeedModifyMethod = false;
    }
}
