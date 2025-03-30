package com.wang.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wang.common.object.entity.Blog;

public interface BlogContentService {


    IPage<Blog> selectBlogPageByLevel(Integer level,Integer useSort,Long currentPage);

    Blog queryBlogByUid(String uid,Integer oid);

}
