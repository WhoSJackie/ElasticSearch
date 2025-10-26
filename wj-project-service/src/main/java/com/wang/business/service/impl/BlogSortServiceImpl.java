package com.wang.business.service.impl;

import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.business.mysqldao.BlogSortDao;
import com.wang.business.service.BlogSortService;
import com.wang.common.constants.SqlConstant;
import com.wang.common.enums.EStatusEnum;
import com.wang.common.enums.StatusEnum;
import com.wang.common.object.entity.BlogSort;
import com.wang.common.object.vo.BlogSortVo;
import com.wang.common.utils.StrUtils;
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
        Page<BlogSort> page = new Page<>();
        page.setCurrent(1);
        page.setSize(10);
        if (blogSortVo==null) {
            // 查询条件为空，返回所有
            List<BlogSort> blogSorts = blogSortDao.selectList(null);
            page.setRecords(blogSorts);
            return page;
        }
        QueryWrapper<BlogSort> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(blogSortVo.getKeyWord()) && !StringUtils.isEmpty(blogSortVo.getKeyWord().trim())){
            queryWrapper.like(SqlConstant.SORT_NAME,blogSortVo.getKeyWord().trim());
        }
        if (StringUtils.isNotEmpty(blogSortVo.getOrderByAscColumn())){
            String column = StrUtils.underlineReplace(new StringBuffer(blogSortVo.getOrderByAscColumn())).toString();
            queryWrapper.orderByAsc(column);
        } else if (StringUtils.isNotEmpty(blogSortVo.getOrderByDescColumn())){
            String column = StrUtils.underlineReplace(new StringBuffer(blogSortVo.getOrderByDescColumn())).toString();
            queryWrapper.orderByAsc(column);
        } else{
            queryWrapper.orderByDesc(SqlConstant.SORT);
        }
        queryWrapper.eq(SqlConstant.STATUS, EStatusEnum.ENABLE.getValue());
        page.setCurrent(blogSortVo.getCurrentPage());
        page.setSize(blogSortVo.getPageSize());
        // todo:排序
        return blogSortDao.selectPage(page,queryWrapper);
    }

    @Override
    public Boolean addSortList(BlogSortVo blogSortVo) {
        BlogSort blogSort = new BlogSort();
        if (StringUtils.isNotEmpty(blogSortVo.getSortName())) blogSort.setSortName(blogSortVo.getSortName());
        if (StringUtils.isNotEmpty(blogSortVo.getContent())) blogSort.setContent(blogSortVo.getContent());
        if (blogSortVo.getSort()!=null) blogSort.setSort(blogSortVo.getSort());
        return blogSortDao.insert(blogSort)>0;
    }

    @Override
    public Boolean updateSortList(BlogSortVo blogSortVo) {
        BlogSort blogSort = new BlogSort();
        if (StringUtils.isEmpty(blogSortVo.getUid())) return false;
        if (StringUtils.isNotEmpty(blogSortVo.getSortName())) blogSort.setSortName(blogSortVo.getSortName());
        if (StringUtils.isNotEmpty(blogSortVo.getContent())) blogSort.setContent(blogSortVo.getContent());
        if (blogSortVo.getSort()!=null) blogSort.setSort(blogSortVo.getSort());
        return blogSortDao.updateById(blogSort)>0;
    }
}
