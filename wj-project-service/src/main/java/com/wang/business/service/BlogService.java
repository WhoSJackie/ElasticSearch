package com.wang.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wang.common.object.entity.Blog;
import com.wang.common.object.vo.BlogVo;
import com.wang.common.object.vo.PageInfo;
import org.springframework.stereotype.Service;

public interface BlogService {

    void setBlogTag(Blog blog);

    void setBlogSort(Blog blog);

    void setBlogPicture(Blog blog);

    IPage<Blog> getBlogPageList(BlogVo blogVo);


}
