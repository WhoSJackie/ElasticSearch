package com.wang.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wang.business.service.BlogContentService;
import com.wang.business.service.BlogService;
import com.wang.business.service.BlogSortService;
import com.wang.common.object.entity.Blog;
import com.wang.common.object.vo.BlogVo;
import com.wang.common.object.vo.ResVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blog")
@Api(value = "管理台博客相关接口", tags = {"管理台博客相关接口"})
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogSortService blogSortService;

    @PostMapping("/getPageList")
    @ApiOperation(value = "获取博客列表", notes = "获取博客列表", response = ResVo.class)
    public ResVo<IPage<Blog>> getBlogList(@RequestBody BlogVo blogVo){
        IPage<Blog> blogIPage = blogService.getBlogPageList(blogVo);
        return ResVo.buildSuccessRes(blogIPage);
    }

    @PostMapping("/addBlog")
    @ApiOperation(value = "添加博客", notes = "添加博客", response = ResVo.class)
    public ResVo<String> addBlog(@RequestBody BlogVo blogVo){
        blogService.addBlog(blogVo);
        return ResVo.buildSuccessRes("添加成功!");
    }

    @PostMapping("/editBlog")
    @ApiOperation(value = "编辑博客", notes = "编辑博客", response = ResVo.class)
    public ResVo<String> editBlog(@RequestBody BlogVo blogVo){
        blogService.editBlog(blogVo);
        return ResVo.buildSuccessRes("编辑成功!");
    }

    @PostMapping("/deleteBlog")
    @ApiOperation(value = "删除博客", notes = "删除博客", response = ResVo.class)
    public ResVo<String> deleteBlog(@RequestBody BlogVo blogVo){
        blogService.deleteBlog(blogVo);
        return ResVo.buildSuccessRes("删除成功!");
    }

    @PostMapping("/deleteBatchBlog")
    @ApiOperation(value = "批量删除博客", notes = "批量删除博客", response = ResVo.class)
    public ResVo<String> deleteBatchBlog(@RequestBody List<BlogVo> blogVoList){
        blogService.deleteBatch(blogVoList);
        return ResVo.buildSuccessRes("批量删除成功!");
    }

}
