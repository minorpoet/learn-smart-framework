package com.holysu.demo;

import com.holysu.framework.annotation.Action;
import com.holysu.framework.annotation.Controller;
import com.holysu.framework.mvc.Param;
import com.holysu.framework.mvc.View;

/**
 * @author minorpoet 2018/7/1
 */
@Controller
public class HelloController{

    @Action("get:/hello")
    public View hello(Param param){
        View view = new View("hell.jsp");

        return view;
    }
}
