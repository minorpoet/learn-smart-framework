package com.holysu.framework.mvc;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * 封装请求信息
 *
 * @author minorpoet 2018/6/30
 */
public class Request {

    private String requestMethod;

    private String requsetPath;

    public Request(String requestMethod, String requestPath){
        this.requestMethod = requestMethod;
        this.requsetPath = requestPath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequsetPath() {
        return requsetPath;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
