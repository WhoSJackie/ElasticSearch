package com.wang.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class StrUtils {

    private static final Pattern UPPER_WORD = Pattern.compile("[A-Z]");
    public static String getUUID(){
        String s = UUID.randomUUID().toString().replace("-", "");
        log.info("UUID 调试日志");
        return s;
    }

    public static boolean isEmpty(String str){
        return StringUtils.isEmpty(str);
    }

    public static List<String> StringToList(String val,String code){
        List<String> res = new ArrayList<>();
        if (StrUtils.isEmpty(val)) return res;
        return Arrays.stream(val.split(code)).collect(Collectors.toList());
    }

    public static StringBuffer underlineReplace(StringBuffer str){
        StringBuffer sb = new StringBuffer(str);
        Matcher matcher = UPPER_WORD.matcher(str);
        if (matcher.find()){
            sb = new StringBuffer();
            matcher.appendReplacement(sb,"_"+matcher.group(0).toLowerCase());
            matcher.appendTail(sb);
        }
        return sb;
    }

    public static void main(String[] args) throws IOException {
        // todo:测试spring.factories
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources("META-INF/spring.factories");
        while (resources.hasMoreElements()){
            URL url = resources.nextElement();
            UrlResource resource = new UrlResource(url);
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                System.out.println(entry.getKey());
                System.out.println("*************");
                System.out.println(entry.getValue());
            }
        }
    }



}
