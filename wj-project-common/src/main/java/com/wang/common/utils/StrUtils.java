package com.wang.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
public class StrUtils {

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





}
