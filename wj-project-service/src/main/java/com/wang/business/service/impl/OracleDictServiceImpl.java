package com.wang.business.service.impl;

import com.wang.business.dao.SysDictValueDao;
import com.wang.business.pojo.oracle.SysDictValuePojo;
import com.wang.business.service.OracleDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OracleDictServiceImpl implements OracleDictService {

    @Autowired
    SysDictValueDao sysDictValueDao;

    @Override
    public List<SysDictValuePojo> queryDictItem(SysDictValuePojo pojo) {
        return sysDictValueDao.getSysDictValue(pojo);
    }
}
