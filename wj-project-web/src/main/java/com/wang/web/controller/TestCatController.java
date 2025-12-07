package com.wang.web.controller;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Event;
import com.dianping.cat.message.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

@Slf4j
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

    @RequestMapping("/download")
    public void download(HttpServletResponse response){
        String fileName = "2025年暑假.xlsx";
        InputStream inputStream = null;
        OutputStream outputStream = null;
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Cache-Control", "no-cache");
        try{
            inputStream = this.getClass().getResourceAsStream("/download/2025年暑假.xlsx");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            outputStream = response.getOutputStream();
            outputStream.write(buffer);
            outputStream.flush();
        } catch (Exception e){
            log.error(e.toString());
        } finally{
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }






}
