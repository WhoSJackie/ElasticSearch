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
        return ResVo.buildSuccessRes(pictureSortService.getPageInfo(pictureSortVo));
    }

    @PostMapping("/getPicSortByUid")
    @ApiOperation(value = "通过uid查找图片分类", notes = "通过uid查找图片分类", response = ResVo.class)
    public ResVo<PictureSort> getPicSortByUid(@RequestBody PictureSortVo pictureSortVo){
        return ResVo.buildSuccessRes(pictureSortService.getSortByUid(pictureSortVo));
    }

}
