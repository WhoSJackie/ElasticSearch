package com.wang.business.service.impl;

import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.business.mysqldao.BlogContentDao;
import com.wang.business.mysqldao.TagDao;
import com.wang.business.service.TagService;
import com.wang.common.enums.EStatusEnum;
import com.wang.common.enums.StatusEnum;
import com.wang.common.object.entity.Blog;
import com.wang.common.object.entity.Tag;
import com.wang.common.object.vo.ResVo;
import com.wang.common.object.vo.TagVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TagServiceImpl implements TagService {

    @Autowired
    TagDao tagDao;

    @Autowired
    BlogContentDao blogDao;

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
        if (!StringUtils.isEmpty(tagVo.getKeyWord()) && !StringUtils.isEmpty(tagVo.getKeyWord().trim())){
            queryWrapper.like(Tag::getContent,tagVo.getKeyWord().trim());
        }

        queryWrapper.eq(Tag::getStatus, EStatusEnum.ENABLE.getValue());
        page.setCurrent(tagVo.getCurrentPage());
        page.setSize(tagVo.getPageSize());
        // todo:排序
        return tagDao.selectPage(page,queryWrapper);
    }

    @Override
    public ResVo<String> addBlogTag(TagVo tagVo) {
        if (ObjectUtils.isEmpty(tagVo)){
            log.info("没有新增内容!");
        }
        if (isExistEntity(tagVo)){
            log.info("存在内容相同的tag标签,插入失败!");
            return ResVo.buildErrRes("存在内容相同的tag标签,插入失败!");
        }
        Tag tag = new Tag();
        tag.setContent(tagVo.getContent());
        tag.setSort(tagVo.getSort());
        return tagDao.insert(tag)>0?ResVo.buildSuccessMsgRes("插入成功!"):ResVo.buildErrRes("插入失败!");
    }

    public ResVo<String> updateBlogTag(TagVo tagVo){
        if (ObjectUtils.isEmpty(tagVo)){
            log.info("没有内容!");
        }
        if (!isExistEntity(tagVo)){
            log.info("不存在符合条件的tag标签,更新失败!");
            return ResVo.buildErrRes("不存在符合条件的tag标签,更新失败!");
        }
        Tag tag = new Tag();
        tag.setUid(tagVo.getUid());
        tag.setContent(tagVo.getContent());
        tag.setSort(tagVo.getSort());
        return tagDao.updateById(tag)>0?ResVo.buildSuccessMsgRes("更新成功!"):ResVo.buildErrRes("更新失败!");
    }

    @Override
    public ResVo<String> deleteBatchBlogTag(List<TagVo> tagVoList) {
        List<String> uidList = tagVoList.stream().map(TagVo::getUid).collect(Collectors.toList());
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<Blog>();
        queryWrapper.in(Blog::getTagUid,uidList);
        queryWrapper.eq(Blog::getStatus,EStatusEnum.ENABLE.getValue());
        Long count = blogDao.selectCount(queryWrapper);
        if (count>0){
            String msg = "标签关联的博客没有删除！";
            log.info(msg);
            return ResVo.buildErrRes(msg);
        }
        List<Tag> tags = tagDao.selectBatchIds(uidList);
        tags.forEach(i->{
            i.setStatus(EStatusEnum.DISABLE.getValue());
        });
        return tagDao.updateBatchStatusByIds(uidList)>0? ResVo.buildSuccessRes("删除成功!"): ResVo.buildErrRes("删除失败");
    }

    private boolean isExistEntity(TagVo tagVo){
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(tagVo.getUid())){
            queryWrapper.eq(Tag::getUid,tagVo.getUid());
        } else{
            queryWrapper.eq(Tag::getContent,tagVo.getContent());
        }
        queryWrapper.eq(Tag::getStatus,EStatusEnum.ENABLE.getValue());
        queryWrapper.last("limit 1");
        return tagDao.selectOne(queryWrapper)!=null;
    }
}
