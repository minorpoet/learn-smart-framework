package com.holysu.framework.core;

import com.holysu.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bean 帮助类
 * @author minorpoet 2018/6/30
 */
public class BeanHelper {

    /**
     * Bean 映射， 用于存放 Bean 类型和 Bean 实例的映射关系
     */
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();

    static{
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for(Class<?> beanClass : beanClassSet){
            Object obj = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass, obj);
        }
    }

    public static Map<Class<?>, Object> getBeanMap(){
        return BEAN_MAP;
    }

    /**
     * 获取 bean 实例
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> cls){
        if(!BEAN_MAP.containsKey(cls)){
            throw new RuntimeException("can not get bean by class:" + cls);
        }
        return (T)BEAN_MAP.get(cls);
    }
}
