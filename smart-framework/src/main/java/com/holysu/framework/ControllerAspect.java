package com.holysu.framework;

import com.holysu.framework.annotation.Aspect;
import com.holysu.framework.annotation.Controller;
import com.holysu.framework.proxy.AspectProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * aop 拦截 controllerbh
 *
 * Created by holysu on 2018/7/7.
 */
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);

    private long begin;

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        LOGGER.debug("----------- begin ------------");
        LOGGER.debug(String.format("Class: %s", cls.getName()));
        LOGGER.debug(String.format("method: %s", method.getName()));
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
        LOGGER.debug(String.format(String.format("time: %dms", System.currentTimeMillis() - begin)));
        LOGGER.debug("----------- end ---------------");
    }
}
