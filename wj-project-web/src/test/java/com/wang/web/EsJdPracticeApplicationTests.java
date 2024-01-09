package com.wang.web;

import com.wang.business.pojo.mysql.SysDictItemPojo;
import com.wang.business.pojo.oracle.SysDictValuePojo;
import com.wang.business.service.OracleDictService;
import com.wang.business.service.SysDictService;
import com.wang.business.service.TxtInsertService;
import com.wang.common.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class EsJdPracticeApplicationTests {

    @Autowired
    TxtInsertService txtInsertService;

    @Autowired
    SysDictService sysDictService;

    @Autowired
    OracleDictService oracleDictService;

    @Autowired
    RedisUtil redisUtil;

    @Test
    void contextLoads() {
    }

    @Test
    public void testInsertTxt(){
        // TxtInsertPojo pojo = new TxtInsertPojo("h11000","H11000_QZZCXJRZQ");
        System.out.println(txtInsertService.insertTxtLine());
    }

    @Test
    public void testInsertSys(){
        SysDictItemPojo pojo = new SysDictItemPojo();
        pojo.setDictItem(1);
        pojo.setSubitem("01");
        pojo.setSubitemName("test");
        System.out.println(sysDictService.insertDict(pojo));
    }

    @Test
    public void testQueryDictService(){
        SysDictValuePojo pojo = new SysDictValuePojo();
        pojo.setDictitem(76271);
        List<SysDictValuePojo> valuePojos = oracleDictService.queryDictItem(pojo);
        for (SysDictValuePojo valuePojo : valuePojos) {
            System.out.println(valuePojo);
        }
    }

    @Test
    void testGetAndSet(){
        redisUtil.set("test01","hello01");
        System.out.println(redisUtil.get("test01"));
    }



}
