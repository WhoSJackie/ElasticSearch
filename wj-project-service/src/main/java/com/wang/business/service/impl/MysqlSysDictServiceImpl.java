package com.wang.business.service.impl;

import com.wang.business.mysqldao.SysDictDao;
import com.wang.business.pojo.mysql.SysDictItemPojo;
import com.wang.business.service.SysDictService;
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
