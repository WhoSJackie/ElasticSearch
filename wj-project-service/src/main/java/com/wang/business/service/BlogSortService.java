package com.wang.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wang.common.object.entity.BlogSort;
import com.wang.common.object.vo.BlogSortVo;

import java.util.List;

public interface BlogSortService {

    List<BlogSort> queryBlogSortDetailsByUids(List<String> uidList);

    IPage<BlogSort> getPageSortList(BlogSortVo blogSortVo);

}
