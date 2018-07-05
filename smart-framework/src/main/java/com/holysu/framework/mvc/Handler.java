package com.holysu.framework.mvc;

import java.lang.reflect.Method;

/**
 * 封装 handler 信息
 *
 * @author minorpoet 2018/6/30
 */
public class Handler {


    /**
     * controller 类
     */
    private Class<?> controllerClass;

    /**
     *  Action 方法
     */
    private Method actionMethod;


    public Handler(Class<?> controllerClass, Method actionMethod){
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
