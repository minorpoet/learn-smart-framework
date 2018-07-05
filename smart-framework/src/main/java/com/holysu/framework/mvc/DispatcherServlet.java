package com.holysu.framework.mvc;

import com.holysu.framework.core.BeanHelper;
import com.holysu.framework.core.ConfigHelper;
import com.holysu.framework.core.ControllerHelper;
import com.holysu.framework.core.HelperLoader;
import com.holysu.framework.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求转发器（核心 servlet）
 *
 * @author minorpoet 2018/6/30
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {

        // 初始化相关 Helper 类
        HelperLoader.init();

        ServletContext servletContext = config.getServletContext();

        // 注册助理 jsp 的servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");

        // 注册处理静态资源的默认 Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取请求方法和路径
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();

        // 获取 Action 处理器
        Handler handler = ControllerHelper.gethandler(requestMethod, requestPath);
        if(handler != null){
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);

            // 创建请求参数对象
            Map<String, Object> paramMap = new HashMap<>();
            Enumeration<String> paramNames = req.getParameterNames();
            while(paramNames.hasMoreElements()){
                String paramName = paramNames.nextElement();
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName, paramValue);
            }

            // 读取 http 请求body
            String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
            if(StringUtil.isNotEmpty(body)){
                String[] params = StringUtil.splitString(body, "&");
                if(ArrayUtil.isNotEmpty(params)){
                    for(String param : params){
                        String[] array = StringUtil.splitString(param, "=");
                        if(ArrayUtil.isNotEmpty(array) && array.length == 2){
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName, paramValue);
                        }
                    }
                }
            }

            Param param = new Param(paramMap);

            // 调用 Action 方法
             Method actionMethod = handler.getActionMethod();
             Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
             // 处理 Action 方法返回值
            // 返回视图
            if(result instanceof View){
                View view = (View)result;
                String path = view.getPath();
                if(StringUtil.isNotEmpty(path)){
                    if(path.startsWith("/")){
                        resp.sendRedirect(req.getContextPath() + path);
                    }else{
                        Map<String, Object> model = view.getModel();
                        for(Map.Entry<String, Object> entry : model.entrySet()){
                            req.setAttribute(entry.getKey(), entry.getValue());
                        }
                        req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, resp);
                    }
                }
                // 如果是返回 Data 类型，则输出 json 数据
            }else if(result instanceof  Data){
                Data data = (Data)result;
                Object model = data.getModel();
                if(model != null){
                    resp.setContentType("application/json");
                    resp.setCharacterEncoding("utf-8");
                    PrintWriter writer = resp.getWriter();
                    String json = JsonUtil.toJSON(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }
        }
    }
}
