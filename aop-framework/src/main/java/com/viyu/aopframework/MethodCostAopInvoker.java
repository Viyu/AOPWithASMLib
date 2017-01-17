package com.viyu.aopframework;

/**
 * @author Viyu
 * @desc 方法调用时间统计Invoker实现
 */
public class MethodCostAopInvoker extends AopInvoker {

    private Long mStartTime;

    public MethodCostAopInvoker(String className, String methodName) {
        super(className, methodName);
    }

    @Override
    public void aspectBeforeInvoke() {
        mStartTime = System.currentTimeMillis();
    }

    @Override
    public void aspectAfterInvoke() {
        Long endTime = System.currentTimeMillis();
        System.out.println(String.format("%s.%s consume:%dms", mClassName, mMethodName, endTime - mStartTime));
        //TODO 输出到日志系统
    }
}
