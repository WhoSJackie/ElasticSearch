package com.wang.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.business.mysqldao.BlogContentDao;
import com.wang.business.service.BlogService;
import com.wang.business.service.BlogSortService;
import com.wang.business.service.TagService;
import com.wang.common.constants.Constants;
import com.wang.common.constants.SqlConstant;
import com.wang.common.enums.EStatusEnum;
import com.wang.common.feign.FileFeignClient;
import com.wang.common.object.entity.Blog;
import com.wang.common.object.entity.BlogSort;
import com.wang.common.object.entity.File;
import com.wang.common.object.entity.Tag;
import com.wang.common.object.req.FileRequest;
import com.wang.common.object.vo.BlogVo;
import com.wang.common.object.vo.ResVo;
import com.wang.common.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private FileFeignClient fileFeignClient;

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogSortService blogSortService;

    @Autowired
    private BlogContentDao blogContentDao;

    @Override
    public void setBlogTag(Blog blog) {
        List<String> tagIdList = new ArrayList<>();
        // 收集博客对应的标签
        if (!StrUtils.isEmpty(blog.getTagUid())) tagIdList.addAll(StrUtils.StringToList(blog.getTagUid(), Constants.SYMBOL_COMMA));
        List<Tag> tags = null;
        // 查询博客相关Tag信息
        if (!CollectionUtils.isEmpty(tagIdList)) tags = tagService.queryTagListByUid(tagIdList);
        Map<String,Tag> tagMap = tags.stream().collect(Collectors.toMap(Tag::getUid, i->i));
        List<String> tagUids = StrUtils.StringToList(blog.getTagUid(), Constants.SYMBOL_COMMA);
        if (!CollectionUtils.isEmpty(tagUids)){
            List<Tag> listTag = tagIdList.stream().map(i->tagMap.get(i)).collect(Collectors.toList());
            blog.setTagList(listTag);
        }
    }

    @Override
    public void setBlogSort(Blog blog) {
        List<String> sortIdList = new ArrayList<>();
        // 收集博客对应的分类uid
        if (!StrUtils.isEmpty(blog.getBlogSortUid())) sortIdList.add(blog.getBlogSortUid());
        List<BlogSort> blogSorts = null;
        if (!CollectionUtils.isEmpty(sortIdList)) blogSorts = blogSortService.queryBlogSortDetailsByUids(sortIdList);
        // 查询博客相关分类信息
        Map<String,BlogSort> sortMap = blogSorts.stream().collect(Collectors.toMap(BlogSort::getUid, i->i));
        if (blog.getBlogSortUid()!=null){
            blog.setBlogSort(sortMap.get(blog.getBlogSortUid()));
        }
    }

    @Override
    public void setBlogPicture(Blog blog) {
        List<String> uidList = new ArrayList<>();
        if (!StrUtils.isEmpty(blog.getFileUid())) uidList.addAll(StrUtils.StringToList(blog.getFileUid(), Constants.SYMBOL_COMMA));
        FileRequest request = new FileRequest(uidList);
        ResVo<List<File>> pictureRes = fileFeignClient.getPicture(request);
        List<File> picList = pictureRes.getData();
        if (CollectionUtils.isEmpty(picList)) return;
        Map<String,String> picMap = picList.stream().collect(Collectors.toMap(File::getUid,File::getPicUrl));
        List<String> fileUids = StrUtils.StringToList(blog.getFileUid(), Constants.SYMBOL_COMMA);
        // 将图片信息设置回blog中
        if (!CollectionUtils.isEmpty(fileUids)){
            List<String> list = fileUids.stream().map(i->picMap.get(i)).collect(Collectors.toList());
            blog.setPhotoList(list);
        }
    }

    @Override
    public IPage<Blog> getBlogPageList(BlogVo blogVo) {
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
        if (!StrUtils.isEmpty(blogVo.getTitle()) && !StrUtils.isEmpty(blogVo.getTitle().trim())){
            queryWrapper.eq(Blog::getTitle,blogVo.getKeyWord());
        }
        if (!StrUtils.isEmpty(blogVo.getBlogSortUid()) && !StrUtils.isEmpty(blogVo.getBlogSortUid().trim())){
            queryWrapper.eq(Blog::getBlogSortUid,blogVo.getBlogSortUid());
        }
        if (!StrUtils.isEmpty(blogVo.getTagUid()) && !StrUtils.isEmpty(blogVo.getTagUid().trim())){
            queryWrapper.eq(Blog::getTagUid,blogVo.getTagUid());
        }
        if (!StrUtils.isEmpty(blogVo.getLevelKeyword()) && !StrUtils.isEmpty(blogVo.getLevelKeyword().trim())){
            queryWrapper.eq(Blog::getLevel,blogVo.getLevelKeyword());
        }
        if (!StrUtils.isEmpty(blogVo.getIsPublish())){
            queryWrapper.eq(Blog::getIsPublish,blogVo.getIsPublish());
        }
        if (!StrUtils.isEmpty(blogVo.getIsOriginal())){
            queryWrapper.eq(Blog::getIsOriginal,blogVo.getIsOriginal());
        }
        if (!StrUtils.isEmpty(blogVo.getType())){
            queryWrapper.eq(Blog::getType,blogVo.getType());
        }
        queryWrapper.eq(Blog::getStatus, EStatusEnum.ENABLE);
        Page page = new Page<>();
        page.setCurrent(blogVo.getCurrentPage());
        page.setSize(blogVo.getPageSize());

        // todo:后续补充自定义排序字段
        if (blogVo.getUseSort()!=null && blogVo.getUseSort()==0){
            queryWrapper.orderByDesc(Blog::getCreateTime);
        } else{
            queryWrapper.orderByDesc(Blog::getSort);
        }

        IPage<Blog> blogList = blogContentDao.selectPage(page,queryWrapper);
        List<Blog> list = blogList.getRecords();
        if (list.size()==0) return blogList;

        // 填充分类，标签以及图片信息
        setBlog(list);
        blogList.setRecords(list);
        return blogList;
    }

    private void setBlog(List<Blog> info){
        for (Blog blog : info) {
            setBlogSort(blog);
            setBlogTag(blog);
            setBlogPicture(blog);
        }
    }
}
