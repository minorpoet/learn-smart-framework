package com.holysu.framework.core;

import com.holysu.framework.annotation.Inject;
import com.holysu.framework.util.ArrayUtil;
import com.holysu.framework.util.CollectionUtil;
import com.holysu.framework.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入帮助类
 *
 * @author minorpoet 2018/6/30
 */
public final class IocHelper {
    static{
        // 获取所有 Bean 类与 Bean 实例之间的映射管理（Bean Map）
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if(CollectionUtil.isNotEmpty(beanMap)){
            // 遍历 bean map
            for(Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()){
                Class<?> beanClass = beanEntry.getKey();
                Object beanInstance = beanEntry.getValue();

                Field[] beanFields = beanClass.getDeclaredFields();
                if(ArrayUtil.isNotEmpty(beanFields)){
                    // 遍历 bean 成员变量
                    for(Field beanField : beanFields){
                        // 判断 @Inject 注解, 注入属性
                        if(beanField.isAnnotationPresent(Inject.class)){
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);

                            if(beanFieldInstance != null){
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
