package com.wang.business.service.test;

import com.wang.common.object.entity.test.SysDictItemPojo;

import java.util.List;

public interface SysDictService {

     Boolean insertDict(SysDictItemPojo pojo);

     List<SysDictItemPojo> queryItem(SysDictItemPojo sysDictItemPojo);

}
