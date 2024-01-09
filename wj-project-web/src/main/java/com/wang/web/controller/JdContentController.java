package com.wang.web.controller;

import com.wang.business.pojo.QueryJdContentRes;
import com.wang.business.service.JdContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/eshandler/")
public class JdContentController {

    @Autowired
    JdContentService jdContentService;

    @GetMapping("saveContent/{keyWord}")
    public boolean saveJdContent(@PathVariable("keyWord") String keyWord){
        return jdContentService.saveJdContent(keyWord);
    }

    @GetMapping("queryContent/{keyWord}/{pageNo}/{pageSize}")
    public QueryJdContentRes queryJdContent(@PathVariable("keyWord") String keyWord,
                                            @PathVariable("pageNo") int pageNo,
                                            @PathVariable("pageSize") int pageSize

    ){
        QueryJdContentRes res = null;
        try {
            res = jdContentService.queryJdContent(keyWord,pageNo,pageSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }



}
