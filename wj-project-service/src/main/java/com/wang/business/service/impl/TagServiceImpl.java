package com.wang.business.service.impl;

import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.business.mysqldao.TagDao;
import com.wang.business.service.TagService;
import com.wang.common.enums.EStatusEnum;
import com.wang.common.enums.StatusEnum;
import com.wang.common.object.entity.Tag;
import com.wang.common.object.vo.TagVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagDao tagDao;

    @Override
    public List<Tag> queryTagListByUid(List<String> uids) {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Tag::getUid,uids);
        wrapper.eq(Tag::getStatus, StatusEnum.ENABLE.getValue());
        return tagDao.selectList(wrapper);
    }

    @Override
    public IPage<Tag> getPageTagList(TagVo tagVo) {
        Page<Tag> page = new Page<>();
        page.setCurrent(1);
        page.setSize(10);
        if (tagVo==null){
            List<Tag> tagList = tagDao.selectList(null);
            page.setRecords(tagList);
            return page;
        }
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(tagVo.getContent()) && !StringUtils.isEmpty(tagVo.getContent().trim())){
            queryWrapper.like(Tag::getContent,tagVo.getContent().trim());
        }
        queryWrapper.eq(Tag::getStatus, EStatusEnum.ENABLE.getValue());
        page.setCurrent(tagVo.getCurrentPage());
        page.setSize(tagVo.getPageSize());
        // todo:排序
        return tagDao.selectPage(page,queryWrapper);
    }
}
