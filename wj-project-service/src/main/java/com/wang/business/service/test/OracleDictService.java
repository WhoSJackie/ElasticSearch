package com.wang.business.service.test;

import com.wang.common.object.entity.oracle.SysDictValuePojo;

import java.util.List;

public interface OracleDictService {

    List<SysDictValuePojo> queryDictItem(SysDictValuePojo pojo);

    int insertDictValue(String items,String itemName);
}
