package com.viyu.aopframework;


/**
 * @author Viyu
 * @desc 插入到类中的实际代码
 */
public abstract class AopInvoker {

    /**
     * 类名
     */
    protected final String mClassName;

    /**
     * 方法名
     */
    protected final String mMethodName;

    protected AopInvoker(String className, String methodName) {
        this.mClassName = className;
        this.mMethodName = methodName;
    }

    /**
     * 方法开始前执行的逻辑
     */
    public abstract void aspectBeforeInvoke();

    /**
     * 方法结束后执行的逻辑
     */
    public abstract void aspectAfterInvoke();

    /**
     * 生成一个invoker
     *
     * @param className
     * @param methodName
     * @return
     */
    public static AopInvoker newInvoker(String className, String methodName) {
        return new MethodCostAopInvoker(className, methodName);//TODO: 支持注解方式来替换这里的实现
    }
}
