package com.holysu.framework.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 封装 servlet 的 request 和response
 *
 * @use 在 DispatcherServlet 的 service 方法开头调用init初始化
 * @author minorpoet 2018/7/8
 */
public final class ServletHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServletHelper.class);

    /**
     * 利用 ThreadLocal 使每个线程独立拥有一份 ServletHelper 实例
     */
    private static final ThreadLocal<ServletHelper> SERVLET_HELPER_THREAD_LOCAL = new ThreadLocal<>();

    private HttpServletRequest request;
    private HttpServletResponse response;

    public ServletHelper(HttpServletRequest request, HttpServletResponse response){
        this.request =  request;
        this.response = response;
    }

    /**
     * 初始化
     * @param request
     * @param response
     */
    public static void init(HttpServletRequest request, HttpServletResponse response){
        SERVLET_HELPER_THREAD_LOCAL.set(new ServletHelper(request, response));
    }

    public static void destroy(){
        SERVLET_HELPER_THREAD_LOCAL.remove();
    }

    /**
     * 将属性放入 Request 中
     * @param key
     * @param value
     */
    public static void setRequestAttribute(String key, Object value){
        getRequest().setAttribute(key, value);
    }

    /**
     * 从 Request 中获取属性
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T getRequestAttribute(String key){
        return (T) getRequest().getAttribute(key);
    }

    /**
     * 移除 attribute
     * @param key
     */
    public static void removeRequestAttribute(String key){
        getRequest().removeAttribute(key);
    }

    /**
     * 重定向
     * @param location
     */
    public static void sendRedirect(String location){
        try{
            getResponse().sendRedirect(getRequest().getContextPath() + location);
        }catch (IOException e){
            LOGGER.error("redirect failure", e);
        }
    }

    /**
     * 设置session值
     * @param key
     * @param value
     */
    public static void setSessionAttribute(String key, Object value){
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(String key){
        return getSession().getAttribute(key);
    }

    public static void removeSessionAttribute(String key){
        getSession().removeAttribute(key);
    }

    public static void invalidateSession(){
        getSession().invalidate();
    }

    private static HttpServletRequest getRequest(){
        return SERVLET_HELPER_THREAD_LOCAL.get().request;
    }

    private static HttpServletResponse getResponse(){
        return SERVLET_HELPER_THREAD_LOCAL.get().response;
    }

    private static HttpSession getSession(){
        return getRequest().getSession();
    }

    /**
     * 获取 Servlet 上下文
     * @return
     */
    private  static ServletContext getServletContext(){
        return getRequest().getServletContext();
    }
}
