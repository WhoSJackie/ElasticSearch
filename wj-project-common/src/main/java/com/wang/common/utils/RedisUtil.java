package com.wang.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 设置指定的key value值
     * @param key
     * @param value
     */
    public void set(String key,String value){
        redisTemplate.opsForValue().set(key,value);
    }

    /**
     * 获取指定的key value值
     * @param key
     * @return
     */
    public String get(String key){
        return redisTemplate.opsForValue().get(key);
    }



}
