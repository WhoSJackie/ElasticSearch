package com.wang.web;

import com.wang.business.pojo.TxtInsertPojo;
import com.wang.business.pojo.mysql.Admin;
import com.wang.business.pojo.mysql.Role;
import com.wang.business.pojo.mysql.SysDictItemPojo;
import com.wang.business.pojo.oracle.SysDictValuePojo;
import com.wang.business.service.*;
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

    @Autowired
    AdminService adminService;

    @Autowired
    RoleService roleService;

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
        List<SysDictItemPojo> sysDictItemPojos = sysDictService.queryItem(pojo);
        if (sysDictItemPojos.size()<=0){
            System.out.println(sysDictService.insertDict(pojo));
        }
        // 查询列表
        List<SysDictItemPojo> sysDictItemPojoList = sysDictService.queryItem(null);
        for (SysDictItemPojo itemPojo : sysDictItemPojoList) {
            System.out.println(itemPojo);
        }
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

    @Test
    public void queryPojo(){
        List<Admin> adminList = adminService.queryItemList();
        for (Admin admin : adminList) {
            System.out.println(admin.toString());
        }
        Role role = roleService.queryItem(2L);
        System.out.println(role.toString());
    }




}
