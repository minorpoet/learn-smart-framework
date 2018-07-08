package com.holysu.framework.annotation;

import java.lang.annotation.*;

/**
 * 切面注解
 *
 * @author minorpoet 2018/7/6
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    /**
     * 带有此注解的为目标类
     * @return
     */
    Class<? extends Annotation> value();
}
