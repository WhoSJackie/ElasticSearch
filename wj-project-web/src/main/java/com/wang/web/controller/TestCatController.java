package com.wang.web.controller;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cat")
public class TestCatController {

    @RequestMapping("/catLog")
    public String getTest(){
        Transaction t = Cat.newTransaction("CONTROLLER", "catLog");
        try{
        Cat.logEvent("URL.Server", "serverIp", Event.SUCCESS, "ip=${serverIp}");
        Cat.logError("测试报错",new RuntimeException("测试报错!"));
        } catch (Exception e){
            t.setStatus(e);
            Cat.logError("getTest报错",e);
        } finally{
            t.complete();
        }
        return "This is a test!";
    }


}
