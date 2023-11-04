package com.wang.service;

import com.wang.pojo.oracle.SysDictValuePojo;

import java.util.List;

public interface OracleDictService {

    List<SysDictValuePojo> queryDictItem(SysDictValuePojo pojo);


}
