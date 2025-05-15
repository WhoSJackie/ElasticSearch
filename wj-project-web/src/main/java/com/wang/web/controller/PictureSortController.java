package com.wang.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wang.business.service.PictureSortService;
import com.wang.common.object.entity.PictureSort;
import com.wang.common.object.vo.PictureSortVo;
import com.wang.common.object.vo.ResVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/picSort/")
@Api(value = "图片分类相关接口", tags = {"图片分类相关接口"})
public class PictureSortController {

    @Autowired
    PictureSortService pictureSortService;

    @PostMapping("/getPage")
    @ApiOperation(value = "图片分类分页列表", notes = "图片分类分页列表", response = ResVo.class)
    public ResVo<IPage<PictureSort>> getPageInfo(@RequestBody PictureSortVo pictureSortVo){
        try{
            IPage<PictureSort> pageInfo = pictureSortService.getPageInfo(pictureSortVo);
            return ResVo.buildSuccessRes(pageInfo);
        } catch(Exception e){
            return ResVo.buildErrRes("查询列表失败"+e);
        }
    }

    @PostMapping("/getPicSortByUid")
    @ApiOperation(value = "通过uid查找图片分类", notes = "通过uid查找图片分类", response = ResVo.class)
    public ResVo<PictureSort> getPicSortByUid(@RequestBody PictureSortVo pictureSortVo){
        return ResVo.buildSuccessRes(pictureSortService.getSortByUid(pictureSortVo));
    }

    @PostMapping("/editPicSort")
    @ApiOperation(value = "编辑图片分类", notes = "编辑图片分类", response = ResVo.class)
    public ResVo<String> updatePicSort(@RequestBody PictureSortVo pictureSortVo){
        int i = 0;
        try{
            i = pictureSortService.updatePictureSort(pictureSortVo);
        } catch(Exception e){
            return ResVo.buildErrRes("编辑失败:"+e);
        }
        return i>0 ? ResVo.buildSuccessMsgRes("编辑成功!"):ResVo.buildErrRes("编辑失败!");
    }

    @PostMapping("/addPicSort")
    @ApiOperation(value = "插入图片分类", notes = "插入图片分类", response = ResVo.class)
    public ResVo<String> addPicSort(@RequestBody PictureSortVo pictureSortVo){
        int i = 0;
        try{
            i = pictureSortService.addPictureSort(pictureSortVo);
        } catch(Exception e){
            return ResVo.buildErrRes("插入失败:"+e);
        }
        return i>0 ? ResVo.buildSuccessMsgRes("插入成功!"):ResVo.buildErrRes("插入失败!");
    }

    @GetMapping("/delPicSort")
    @ApiOperation(value = "删除图片分类", notes = "删除图片分类", response = ResVo.class)
    public ResVo<String> deletePictureSort(@RequestParam("uid") String uid){
        return pictureSortService.deletePictureSort(uid);
    }

}
