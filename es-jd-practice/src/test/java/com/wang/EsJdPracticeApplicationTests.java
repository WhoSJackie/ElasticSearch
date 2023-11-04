package com.wang;

import com.wang.pojo.mysql.SysDictItemPojo;
import com.wang.pojo.oracle.SysDictValuePojo;
import com.wang.service.OracleDictService;
import com.wang.service.SysDictService;
import com.wang.service.TxtInsertService;
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



}
