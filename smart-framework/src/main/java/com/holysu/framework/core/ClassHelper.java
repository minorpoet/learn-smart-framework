package com.holysu.framework.core;

import com.holysu.framework.annotation.Controller;
import com.holysu.framework.annotation.Service;
import com.holysu.framework.util.ClassUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * 根据条件获取相关类
 *
 * @author minorpoet 2018/6/28
 */
public class ClassHelper {

    /**
     * 定义类集合（用于存放所加载的类）
     */
    private static final Set<Class<?>> CLASS_SET;

    static{
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }


    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    /**
     * 获取应用包名下所有的 Service 类
     * @return
     */
    public static Set<Class<?>> getServiceClassSet(){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for(Class<?> cls : CLASS_SET){
            if(cls.isAnnotationPresent(Service.class)){
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下所有的 Controller 类
     * @return
     */
    public static Set<Class<?>> getControllerClassSet(){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for(Class<?> cls : CLASS_SET){
            if(cls.isAnnotationPresent(Controller.class)){
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下所有的 bean
     * @return
     */
    public static Set<Class<?>> getBeanClassSet(){
        Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }

    /**
     * 获取应用包名下某父类（或接口）的所有子类（或实现类）
     * @param superClass
     * @return
     */
    public static Set<Class<?>> getClassSetBySuper(Class<?> superClass){
        Set<Class<?>> classSet = new HashSet<>();
        for(Class<?> cls : CLASS_SET){
            if(superClass.isAssignableFrom(cls) && !superClass.equals(cls)){
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名个下带有某注解的所有类
     * @param annotationClass
     * @return
     */
    public static Set<Class<?>> getClassSetByAnnocation(Class<? extends Annotation> annotationClass){
        Set<Class<?>> classSet = new HashSet<>();
        for(Class<?> cls : CLASS_SET){
            if(cls.isAnnotationPresent(annotationClass)){
                classSet.add(cls);
            }
        }
        return classSet;
    }
}
