package com.wang.business.service.impl;

import com.wang.business.dao.TxtInsertDao;
import com.wang.business.pojo.TxtInsertPojo;
import com.wang.business.service.TxtInsertService;
import com.wang.business.utils.TxtFileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
