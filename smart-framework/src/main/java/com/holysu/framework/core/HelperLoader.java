package com.holysu.framework.core;

import com.holysu.framework.util.ClassUtil;

/**
 * 加载对应的 helper 类
 *
 * @author minorpoet 2018/6/30
 */
public final class HelperLoader {

    public static void init(){
        Class<?> [] classList = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class,
        };

        for(Class<?> cls : classList){
            ClassUtil.loadClass(cls.getName());
        }
    }
}
