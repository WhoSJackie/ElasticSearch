package com.wang.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wang.business.service.BlogSortService;
import com.wang.business.service.TagService;
import com.wang.common.object.entity.BlogSort;
import com.wang.common.object.entity.Tag;
import com.wang.common.object.vo.BlogSortVo;
import com.wang.common.object.vo.ResVo;
import com.wang.common.object.vo.TagVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tag")
@Api(value = "博客标签相关接口", tags = {"博客标签相关接口"})
public class TagController {


    @Autowired
    private TagService tagService;

    @PostMapping("/getPageList")
    @ApiOperation(value = "获取博客标签列表", notes = "获取博客标签列表", response = ResVo.class)
    public ResVo<IPage<Tag>> getBlogTagist(@RequestBody TagVo tagVo){
        IPage<Tag> blogTagIPage = tagService.getPageTagList(tagVo);
        return ResVo.buildSuccessRes(blogTagIPage);
    }

    @PostMapping("/addBlogTag")
    @ApiOperation(value = "新增博客标签列表", notes = "新增博客标签列表", response = ResVo.class)
    public ResVo<String>addBlogTag(@RequestBody TagVo tagVo){
        return tagService.addBlogTag(tagVo);
    }

    @PostMapping("/editBlogTag")
    @ApiOperation(value = "编辑博客标签列表", notes = "编辑博客标签列表", response = ResVo.class)
    public ResVo<String> updateBlogTag(@RequestBody TagVo tagVo){
        return  tagService.updateBlogTag(tagVo);
    }

    @PostMapping("/deleteBatch")
    @ApiOperation(value = "删除博客标签列表", notes = "删除博客标签列表", response = ResVo.class)
    public ResVo<String> deleteBatchBlogTag(@RequestBody List<TagVo> tagVoList){
        return tagService.deleteBatchBlogTag(tagVoList);
    }











}
