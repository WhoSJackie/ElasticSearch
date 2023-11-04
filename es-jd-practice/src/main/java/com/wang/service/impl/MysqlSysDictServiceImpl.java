package com.wang.service.impl;

import com.wang.mysqldao.SysDictDao;
import com.wang.pojo.mysql.SysDictItemPojo;
import com.wang.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MysqlSysDictServiceImpl implements SysDictService {

    @Autowired
    SysDictDao sysDictDao;

    @Override
    public Boolean insertDict(SysDictItemPojo pojo) {
        return sysDictDao.insert(pojo);
    }
}
