package com.viyu.aop.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;


/**
 * @author viyu
 * @desc AOP method visitor，主要做的就是在方法中插入了AopInvoker的代码
 */
class AopMethodVisitor extends AdviceAdapter {

    private int mInvokerVarIndex = 0;

    private final String mClassName;
    private final String mMethodName;

    public AopMethodVisitor(int api, MethodVisitor originMV, int access, String desc, String className, String methodName) {
        super(api, originMV, access, methodName, desc);
        mClassName = className;
        mMethodName = methodName;
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
        beginAspect();
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
        afterAspect();
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        super.visitMaxs(maxStack + 2, maxLocals + 1);
    }

    /**
     * 在方法开始插入AopInvoker.aspectBeforeInvoke()
     */
    private void beginAspect() {
        if (mv == null) {
            return;
        }

        mv.visitLdcInsn(mClassName);
        mv.visitLdcInsn(mMethodName);
        mv.visitMethodInsn(INVOKESTATIC, "com/viyu/aopframework/AopInvoker", "newInvoker", "(Ljava/lang/String;Ljava/lang/String;)Lcom/viyu/aopframework/AopInvoker;", false);
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKEVIRTUAL, "com/viyu/aopframework/AopInvoker", "aspectBeforeInvoke", "()V", false);
        mInvokerVarIndex = newLocal(Type.getType("Lcom/viyu/aopframework/AopInvoker;"));
        mv.visitVarInsn(ASTORE, mInvokerVarIndex);
    }

    /**
     * 在方法结束插入AopInvoker.aspectAfterInvoke()
     */
    private void afterAspect() {
        if (mv == null) {
            return;
        }
        mv.visitVarInsn(ALOAD, mInvokerVarIndex);
        mv.visitMethodInsn(INVOKEVIRTUAL, "com/viyu/aopframework/AopInvoker", "aspectAfterInvoke", "()V", false);
    }
}
