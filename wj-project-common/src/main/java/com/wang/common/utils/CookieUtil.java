package com.wang.common.utils;

import org.bouncycastle.util.encoders.UTF8;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.Encoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class CookieUtil {

    public static void setCookie(String cookieName,String cookieValue,Integer maxAge,boolean isEncode){
        doSetCookie(cookieName,cookieValue,maxAge,isEncode);
    }

    private static void doSetCookie(String cookieName,String cookieValue,Integer maxAge,boolean isEncode){
        try{
            ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            if (attributes!=null){
                HttpServletRequest request = attributes.getRequest();
                HttpServletResponse response = attributes.getResponse();
                if (cookieValue==null){
                    cookieValue = "";
                } else if(isEncode){
                    URLEncoder.encode(cookieValue, StandardCharsets.UTF_8.displayName());
                }
                Cookie cookie = new Cookie(cookieName,cookieValue);
                if (maxAge>0){
                    cookie.setMaxAge(maxAge);
                }
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
