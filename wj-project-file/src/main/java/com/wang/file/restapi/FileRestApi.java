package com.wang.file.restapi;

import com.wang.common.object.req.FileRequest;
import com.wang.common.object.resp.FileResponse;
import com.wang.common.object.vo.FileUploadVo;
import com.wang.common.object.vo.ResVo;
import com.wang.common.object.entity.File;
import com.wang.file.dao.FileSortDao;
import com.wang.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/file/")
public class FileRestApi {

    @Autowired
    FileService fileService;


    @PostMapping("/getPictureByUids")
    ResVo<List<File>>  getPicture(@RequestBody FileRequest request){
        if (request== null){
            return ResVo.buildErrRes("请求为空");
        }
        List<File> pictureList = fileService.getPictureByUids(request.getUidList());
        return ResVo.buildSuccessRes(pictureList);
    }

    @PostMapping("/pictures")
    ResVo<FileResponse> uploadPicture(@RequestPart("files") List<MultipartFile> files,
                                      @RequestParam("source")String source,
                                      @RequestParam("userUid")String userUid,
                                      @RequestParam("adminUid")String adminUid,
                                      @RequestParam("projectName")String projectName,
                                      @RequestParam("sortName")String sortName,
                                      @RequestParam("token") String token){
        FileUploadVo fileUploadVo = new FileUploadVo();
        FileResponse fileResponse = null;
        try{
            fileUploadVo.setFiledatas(files);
            fileUploadVo.setSource(source);
            fileUploadVo.setUserUid(userUid);
            fileUploadVo.setAdminUid(adminUid);
            fileUploadVo.setProjectName(projectName);
            fileUploadVo.setSortName(sortName);
            fileUploadVo.setToken(token);
            fileResponse = fileService.uploadPicture(fileUploadVo);
        } catch (Exception e){
            return ResVo.buildErrRes("上传失败"+e);
        }
        return ResVo.buildSuccessRes(fileResponse);
    }

    @PostMapping("/getAllPicture")
    ResVo<List<File>>  getAllPicture(){
        List<File> pictureList = fileService.getAllPicture();
        return ResVo.buildSuccessRes(pictureList);
    }


}
