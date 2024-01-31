package com.wang.business.service;

import com.wang.business.pojo.mysql.SysDictItemPojo;

import java.util.List;

public interface SysDictService {

     Boolean insertDict(SysDictItemPojo pojo);

     List<SysDictItemPojo> queryItem(SysDictItemPojo sysDictItemPojo);

}
