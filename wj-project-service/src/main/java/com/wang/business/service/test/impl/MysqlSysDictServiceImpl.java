package com.wang.business.service.test.impl;

import com.wang.business.mysqldao.test.SysDictDao;
import com.wang.common.object.entity.test.SysDictItemPojo;
import com.wang.business.service.test.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MysqlSysDictServiceImpl implements SysDictService {

    @Autowired
    SysDictDao sysDictDao;

    @Override
    public Boolean insertDict(SysDictItemPojo pojo) {
        return sysDictDao.insert(pojo);
    }

    @Override
    public List<SysDictItemPojo> queryItem(SysDictItemPojo sysDictItemPojo) {
        if (sysDictItemPojo==null){
            sysDictItemPojo = new SysDictItemPojo();
        }
        return sysDictDao.querySysDictValue(sysDictItemPojo);
    }


}
