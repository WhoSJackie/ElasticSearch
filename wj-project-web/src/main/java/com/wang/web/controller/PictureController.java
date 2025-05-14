package com.wang.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wang.business.service.PictureService;
import com.wang.common.feign.FileFeignClient;
import com.wang.common.object.entity.Picture;
import com.wang.common.object.resp.FileResponse;
import com.wang.common.object.vo.FileUploadVo;
import com.wang.common.object.vo.PictureVo;
import com.wang.common.object.vo.ResVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pic")
public class PictureController {

    @Autowired
    PictureService pictureService;

    @Autowired
    FileFeignClient fileFeignClient;

    @PostMapping("/getPage")
    @ApiOperation(value = "图片分页列表", notes = "图片分类分页列表", response = ResVo.class)
    public ResVo<IPage<Picture>> getPageInfo(@RequestBody PictureVo pictureVo){
        return ResVo.buildSuccessRes(pictureService.getPicPage(pictureVo));
    }

    @PostMapping("/pictures")
    public ResVo<FileResponse> uploadPicture(@RequestParam("filedatas") List<MultipartFile> files,
                       @RequestParam("source")String source,
                       @RequestParam("userUid")String userUid,
                       @RequestParam("adminUid")String adminUid,
                       @RequestParam("projectName")String projectName,
                       @RequestParam("sortName")String sortName,
                       @RequestParam("token") String token
                       ){
        return fileFeignClient.uploadPicture(files,source,userUid,adminUid,projectName,sortName,token);
    }

    @PostMapping("/addPictures")
    public ResVo<String> insertPicture(@RequestBody List<PictureVo> pictureVoList){
        int cnt = pictureService.addPicture(pictureVoList);
        if (cnt==pictureVoList.size()) {
            return ResVo.buildSuccessRes("关联成功!");
        }
        return ResVo.buildErrRes(String.format("上传图片失败，应该关联%d,实际关联%d",pictureVoList.size(),cnt));
    }

    @GetMapping("/deletePictures")
    public ResVo<String> deletePictures(@RequestParam("delPic") String picUrls){
        try {
            pictureService.deleteBatchPictures(picUrls);
        } catch(Exception e){
            return ResVo.buildErrRes("批量删除失败！");
        }
        return ResVo.buildSuccessRes("批量删除成功!");
    }



}
