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

    @Autowired
    RuleItemService ruleItemService;

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

    @Test
    /**
     * cisp002校验规则非空核对工具
     */
    public void testRuleItem(){
        List<String> list = ruleItemService.nullableRuleVerify("C:\\Users\\jiami\\Desktop\\table.docx","B6049");
        if (list.size()==0) {
            System.out.println("校验通过!");
            return;
        }
        for (String s : list) {
            System.out.println(s);
        }
    }

    @Test
    /**
     * dictvalue简单插入
     */
    public void insertDictValue(){
        String item  = "一年以内(含一年)贷款利率，一至五年(含五年)贷款利率，五年以上贷款基准利率，五年以下(含五年)个人住房公积金贷款，五年以上个人住房公积金贷款，1年期贷款市场报价利率，5年期贷款市场报价利率，隔夜回购定盘利率FR001，7天回购定盘利率FRO07，SHIBOR-1W-120，SHIBOR-1W-750，SHIBOR-1W-1250，SHIBOR-3M，SHIBOR-3M-5，SHIBOR-3M-10，SHIBOR-3M-120，SHIBOR-6M-5，SHIBOR-6M-7，SHIBOR-6M-20，SHIBOR-1Y-5，SHIBOR-1Y-10，SHIBOR-1Y-15，SHIBOR-1Y-20，SHIBOR-1Y-30，LIBOR-USD-3M，LIBOR-USD-6M，其他-信托公司自定义";
        System.out.println(oracleDictService.insertDictValue(item,""));
    }




}
