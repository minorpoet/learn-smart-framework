package com.holysu.framework.core;

import com.holysu.framework.annotation.Action;
import com.holysu.framework.mvc.Handler;
import com.holysu.framework.mvc.Request;
import com.holysu.framework.util.ArrayUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author minorpoet 2018/6/30
 */
public class ControllerHelper {

    /**
     * 用来存放请求与处理器的映射关系
     * */
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<>();

    static{
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        // 遍历 controller
        for(Class<?> controllerClass : controllerClassSet){
            Method[] methods = controllerClass.getDeclaredMethods();
            if(ArrayUtil.isNotEmpty(methods)){
                // 遍历controller中的方法
                for(Method method : methods){
                    // 判断是否带有 @Action 注解
                    if(method.isAnnotationPresent(Action.class)){
                        Action action = method.getAnnotation(Action.class);
                        String mapping = action.value();

                        // Action 中定义的规则是 {httpmethod}:{url}
                        if(mapping.matches("\\w+:/\\w*")){
                            String[] array = mapping.split(":");
                            if(ArrayUtil.isNotEmpty(array) && array.length == 2){
                                String requestMethod = array[0];
                                String requestPath = array[1];

                                Request request = new Request(requestMethod, requestPath);
                                Handler handler = new Handler(controllerClass, method);

                                // 初始化请求处理器的映射
                                ACTION_MAP.put(request, handler);
                            }
                        }
                    }
                }
            }
        }
    }

    public static Handler gethandler(String requestMethod, String requestPath){
        // Request 的 equals 方法已经重写了， 不用担心
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}

