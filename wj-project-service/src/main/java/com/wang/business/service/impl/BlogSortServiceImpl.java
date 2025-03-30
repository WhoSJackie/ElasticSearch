package com.wang.business.service.impl;

import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.business.mysqldao.BlogSortDao;
import com.wang.business.service.BlogSortService;
import com.wang.common.enums.EStatusEnum;
import com.wang.common.enums.StatusEnum;
import com.wang.common.object.entity.BlogSort;
import com.wang.common.object.vo.BlogSortVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BlogSortServiceImpl implements BlogSortService {

    @Autowired
    BlogSortDao blogSortDao;


    @Override
    public List<BlogSort> queryBlogSortDetailsByUids(List<String> uidList) {
        LambdaQueryWrapper<BlogSort> wrapper = new LambdaQueryWrapper();
        wrapper.in(BlogSort::getUid,uidList);
        wrapper.eq(BlogSort::getStatus, StatusEnum.ENABLE.getValue());
        List<BlogSort> blogSorts = blogSortDao.selectList(wrapper);
        return blogSorts;
    }

    @Override
    public IPage<BlogSort> getPageSortList(BlogSortVo blogSortVo) {
        List<BlogSort> res = new ArrayList<>();
        Page<BlogSort> page = new Page<>();
        page.setCurrent(1);
        page.setSize(10);
        if (blogSortVo==null) {
            // 查询条件为空，返回所有
            List<BlogSort> blogSorts = blogSortDao.selectList(null);
            page.setRecords(blogSorts);
            return page;
        }
        LambdaQueryWrapper<BlogSort> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(blogSortVo.getSortName()) && !StringUtils.isEmpty(blogSortVo.getSortName().trim())){
            queryWrapper.like(BlogSort::getSortName,blogSortVo.getSortName().trim());
        }
        queryWrapper.eq(BlogSort::getStatus, EStatusEnum.ENABLE.getValue());
        page.setCurrent(blogSortVo.getCurrentPage());
        page.setSize(blogSortVo.getPageSize());
        // todo:排序
        return blogSortDao.selectPage(page,queryWrapper);
    }
}
