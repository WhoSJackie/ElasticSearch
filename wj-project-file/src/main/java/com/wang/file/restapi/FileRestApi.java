package com.wang.file.restapi;

import com.wang.common.object.req.FileRequest;
import com.wang.common.object.vo.ResVo;
import com.wang.common.object.entity.File;
import com.wang.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/file/")
public class FileRestApi {

    @Autowired
    FileService fileService;

    @PostMapping("/getPicture")
    ResVo<List<File>>  getPicture(@RequestBody FileRequest request){
        if (request== null){
            return ResVo.buildErrRes("请求为空");
        }
        List<File> pictureList = fileService.getPicture(request.getUidList());
        return ResVo.buildSuccessRes(pictureList);
    }


}
