package com.wang.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public enum RedisEnum {

    LOGIN_LIMIT("LOGIN_LIMIT%s%s"),
    LOGIN_TOKEN_KEY("LOGIN_TOKEN_KEY%s%s"),
    LOGIN_UUID_KEY("LOGIN_UUID_KEY%s%s"),
    IP_ADDRESS("IP_ADDRESS%s%s")
    ;

    private String value;

    public String getRedisKey(Object... param){
        return String.format(value,param);
    }


}
