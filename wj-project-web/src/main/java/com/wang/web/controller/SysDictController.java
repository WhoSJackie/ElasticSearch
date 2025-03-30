package com.wang.web.controller;


import com.wang.business.service.SysDictDataService;
import com.wang.common.object.vo.ResVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/dict")
@RestController
public class SysDictController {

    @Autowired
    private SysDictDataService sysDictDataService;

    @GetMapping("/getSingle")
    public ResVo<Map<String,Object>> getDictInfoByTypeList(@RequestParam("dictType") String dictType){
        return ResVo.buildSuccessRes(sysDictDataService.getDictListByType(dictType));
    }

    @PostMapping("/getMultiple")
    public ResVo<Map<String,Object>> getDictInfoByTypeList(@RequestBody List<String> dictTypeList){
        return ResVo.buildSuccessRes(sysDictDataService.getDictListByTypeList(dictTypeList));
    }

}
