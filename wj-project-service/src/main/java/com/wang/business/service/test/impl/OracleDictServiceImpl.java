package com.wang.business.service.test.impl;

import com.wang.business.dao.SysDictValueDao;
import com.wang.common.object.entity.oracle.SysDictIndexPoJo;
import com.wang.common.object.entity.oracle.SysDictValuePojo;
import com.wang.business.service.test.OracleDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OracleDictServiceImpl implements OracleDictService {

    @Autowired
    SysDictValueDao sysDictValueDao;

    @Override
    public List<SysDictValuePojo> queryDictItem(SysDictValuePojo pojo) {
        return sysDictValueDao.getSysDictValue(pojo);
    }

    /**
     * 简单插入字典值，字典值自增
     * @param items
     * @return
     */
    public int insertDictValue(String items,String itemName){
        // 查找最新的字典值
        int latestDictItem = sysDictValueDao.getLatestDictItem();
        int newDictItem = latestDictItem+1;
        int index=0;
        String[] strs = items.split("，");
        List<SysDictValuePojo> res = new ArrayList<>();
        // 拼装对象list
        for (String str : strs) {
            SysDictValuePojo pojo = new SysDictValuePojo();
            pojo.setDictitem(newDictItem);
            pojo.setSubitem(String.valueOf(index++));
            pojo.setSubitemname(str);
            res.add(pojo);
        }
        int cnt=0;
        for (SysDictValuePojo re : res) {
            cnt+=sysDictValueDao.insertDictValue(re);
        }
        if (cnt==res.size()){
            // 插入index
            SysDictIndexPoJo po = new SysDictIndexPoJo();
            po.setDictitem(newDictItem);
            po.setItemname(itemName);
            po.setValidlen(1);
            po.setDictgroupid("0");
            cnt+=sysDictValueDao.insertDictIndex(po);
        }
        return cnt;
    }


}
