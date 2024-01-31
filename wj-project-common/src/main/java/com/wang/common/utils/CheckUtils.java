package com.wang.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtils {

    private final static String CHECK_EMAIL_REGEX = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    private static final String CHECK_MOBILE_NUMBER_REGEX = "^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|17[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";

    public static boolean  checkEmail(String value){
        boolean flag = false;
        try{
            Pattern regex = Pattern.compile(CHECK_EMAIL_REGEX);
            Matcher matcher = regex.matcher(value);
            flag = matcher.matches();
        } catch (Exception e ){
            flag = false;
        }
        return flag;
    }

    public static boolean  checkPhoneNumber(String value){
        boolean flag = false;
        try{
            Pattern regex = Pattern.compile(CHECK_MOBILE_NUMBER_REGEX);
            Matcher matcher = regex.matcher(value);
            flag = matcher.matches();
        } catch (Exception e ){
            flag = false;
        }
        return flag;
    }

}
