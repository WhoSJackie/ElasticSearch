package com.wang.service.impl;

import com.wang.dao.TxtInsertDao;
import com.wang.pojo.TxtInsertPojo;
import com.wang.service.TxtInsertService;
import com.wang.util.TxtFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class TxtInsertServiceImpl implements TxtInsertService {

    @Autowired
    TxtInsertDao txtInsertDao;

    public boolean insertTxtLine(){
        // 读取txt文件，转换为pojo
        List<TxtInsertPojo> txtInsertPojoList = TxtFileUtils.convertToPojo("C:\\Users\\jiami\\Desktop\\InsertFile.txt");
        boolean flag = true;
        for (TxtInsertPojo s : txtInsertPojoList) {
            flag = flag&&txtInsertDao.insertIntoMappingTb(s);
        }
        return flag;
    }



}
