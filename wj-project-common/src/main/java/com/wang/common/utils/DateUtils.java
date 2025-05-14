package com.wang.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class DateUtils {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String getNowTime(){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    public static String getDateStr(Date date,long secondTimes){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        try{
            long time = date.getTime()+1000*secondTimes;
            return sdf.format(time);
        } catch (Exception e){
            log.error("时间转换失败");
        }
        return "";
    }

    public static Long getSecondBetweenTwo(Date first,Date second){
        return Math.abs(first.getTime()-second.getTime())/1000;
    }

}
