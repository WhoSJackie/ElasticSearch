package com.wang.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wang.business.service.BlogService;
import com.wang.business.service.BlogSortService;
import com.wang.common.object.entity.Blog;
import com.wang.common.object.entity.BlogSort;
import com.wang.common.object.vo.BlogSortVo;
import com.wang.common.object.vo.BlogVo;
import com.wang.common.object.vo.ResVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blogSort")
@Api(value = "博客分类相关接口", tags = {"博客分类相关接口"})
public class BlogSortController {


    @Autowired
    private BlogSortService blogSortService;

    @PostMapping("/getPageList")
    @ApiOperation(value = "获取博客分类列表", notes = "获取博客分类列表", response = ResVo.class)
    public ResVo<IPage<BlogSort>> getBlogSortList(@RequestBody BlogSortVo blogSortVo){
        IPage<BlogSort> blogSortIPage = blogSortService.getPageSortList(blogSortVo);
        return ResVo.buildSuccessRes(blogSortIPage);
    }

    @PostMapping("/addBlogSortList")
    @ApiOperation(value = "新增博客分类", notes = "新增博客分类", response = ResVo.class)
    public ResVo<String> addBlogSortList(@RequestBody BlogSortVo blogSortVo){
        Boolean flag = blogSortService.addSortList(blogSortVo);
        if (flag) return ResVo.buildSuccessMsgRes("添加成功!");
        else return ResVo.buildErrRes("添加失败！");
    }

    @PostMapping("/editBlogSortList")
    @ApiOperation(value = "修改博客分类", notes = "修改博客分类", response = ResVo.class)
    public ResVo<String> editBlogSortList(@RequestBody BlogSortVo blogSortVo){
        Boolean flag = blogSortService.updateSortList(blogSortVo);
        if (flag) return ResVo.buildSuccessMsgRes("修改成功!");
        else return ResVo.buildErrRes("修改失败！");
    }





}
