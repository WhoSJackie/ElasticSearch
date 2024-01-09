package com.wang.business.service;

import com.wang.business.pojo.oracle.SysDictValuePojo;

import java.util.List;

public interface OracleDictService {

    List<SysDictValuePojo> queryDictItem(SysDictValuePojo pojo);


}
