package com.wang.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
@Slf4j
public class StringUtils {

    public static String getUUID(){
        String s = UUID.randomUUID().toString().replace("-", "");
        log.info("UUID 调试日志");
        return s;
    }

    public static boolean isEmpty(String str){
        return StringUtils.isEmpty(str);
    }


}
