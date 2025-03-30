package com.wang.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wang.business.service.BlogContentService;
import com.wang.common.object.entity.Blog;
import com.wang.common.object.vo.ResVo;
import com.wang.common.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blogContent/")
public class BlogContentController {

    @Autowired
    BlogContentService blogContentService;

    @PostMapping("getBlogByLevel")
    public ResVo<List<Blog>>  getBlogByLevel(@RequestParam("level") Integer level,
                                             @RequestParam("useSort") Integer useSort,
                                             @RequestParam("currentPage") Long currentPage){
        IPage<Blog> blogIPage = blogContentService.selectBlogPageByLevel(level, useSort, currentPage);
        return ResVo.buildSuccessRes(blogIPage.getRecords());
    }

    @RequestMapping("getBlogByUid")
    public ResVo<Blog> getBlogByUid(@RequestParam("uid") String uid,
                                    @RequestParam("oid") Integer oid){
        if (StrUtils.isEmpty(uid) && oid==0){
            return ResVo.buildErrRes("uid或者oid无效，无法获取信息!");
        }
        Blog blog = null;
        try{
            blog = blogContentService.queryBlogByUid(uid,oid);
        } catch (Exception e){
            return ResVo.buildErrRes(e.toString());
        }
        return ResVo.buildSuccessRes(blog);
    }


}
