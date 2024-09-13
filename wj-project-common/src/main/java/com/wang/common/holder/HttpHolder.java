package com.wang.common.holder;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class HttpHolder {

    public static HttpServletRequest getRequest(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes==null){
            return null;
        }
        return servletRequestAttributes.getRequest();
    }


}
