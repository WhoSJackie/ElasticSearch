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







}
