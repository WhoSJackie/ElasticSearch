package com.wang.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class TestBeanInitService {

    Map<String,String> cacheMap = new HashMap<>();

    public Map<String, String> getCacheMap() {
        return cacheMap;
    }


    @PostConstruct
    public void init(){
        System.out.println("Bean Init finished...");
        cacheMap.put("msg","Init finished!");
        cacheMap.put("value","Invoke this method!");
        System.out.println("cachemap start success...");
    }


}
