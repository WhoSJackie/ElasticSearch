package com.wang.common.utils;

import org.apache.commons.lang.StringUtils;

public class FileUtils {

    public static String getExpandedName(String name){
        if (StringUtils.isNotEmpty(name)) {
            String[] split = name.split("\\.");
            if (split.length==2){
                return split[1];
            }
        }
        return null;
    }

    public static String genFileName(String originName){
        StringBuffer sb = new StringBuffer();
        String type = getExpandedName(originName);
        if (type==null) return null;
        sb.append(System.currentTimeMillis()).append(".").append(type);
        return sb.toString();
    }



}
