package com.wang.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.business.mysqldao.AdminDao;
import com.wang.business.mysqldao.BlogContentDao;
import com.wang.business.mysqldao.SystemConfigDao;
import com.wang.business.service.*;
import com.wang.common.constants.Constants;
import com.wang.common.constants.SqlConstant;
import com.wang.common.enums.BlogTypeEnum;
import com.wang.common.enums.EOriginalEnum;
import com.wang.common.enums.EStatusEnum;
import com.wang.common.feign.FileFeignClient;
import com.wang.common.holder.HttpHolder;
import com.wang.common.object.entity.*;
import com.wang.common.object.req.FileRequest;
import com.wang.common.object.vo.BlogVo;
import com.wang.common.object.vo.ResVo;
import com.wang.common.utils.StrUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
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

    @Autowired
    private SysParamsService sysParamsService;

    @Autowired
    private AdminService adminService;

    @Override
    public void setBlogTag(Blog blog) {
        List<String> tagIdList = new ArrayList<>();
        // 收集博客对应的标签
        if (!StrUtils.isEmpty(blog.getTagUid())) tagIdList.addAll(StrUtils.StringToList(blog.getTagUid(), Constants.SYMBOL_COMMA));
        List<Tag> tags = null;
        // 查询博客相关Tag信息
        if (!CollectionUtils.isEmpty(tagIdList)) {
            tags = tagService.queryTagListByUid(tagIdList);
            blog.setTagList(tags);
        }
    }

    @Override
    public void setBlogSort(Blog blog) {
        List<String> sortIdList = new ArrayList<>();
        // 收集博客对应的分类uid
        if (!StrUtils.isEmpty(blog.getBlogSortUid())) sortIdList.add(blog.getBlogSortUid());
        List<BlogSort> blogSorts = null;
        if (!CollectionUtils.isEmpty(sortIdList)) {
            blogSorts = blogSortService.queryBlogSortDetailsByUids(sortIdList);
            blog.setBlogSort(blogSorts.get(0));
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
        List<String> photoList = picList.stream().map(File::getPicUrl).collect(Collectors.toList());
        blog.setPhotoList(photoList);
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
        queryWrapper.eq(Blog::getStatus, EStatusEnum.ENABLE.getValue());


        // todo:后续补充自定义排序字段
        if (blogVo.getUseSort()==null || (blogVo.getUseSort()!=null && blogVo.getUseSort()==0)){
            queryWrapper.orderByDesc(Blog::getCreateTime);
        } else{
            queryWrapper.orderByDesc(Blog::getSort);
        }
        Page<Blog> page = new Page<>();
        page.setCurrent(blogVo.getCurrentPage());
        page.setSize(blogVo.getPageSize());
        IPage<Blog> blogList = blogContentDao.selectPage(page,queryWrapper);
        List<Blog> list = blogList.getRecords();
        if (list.size()==0) return blogList;
        // 填充分类，标签以及图片信息
        // todo:可以优化
        setBlog(list);
        return blogList;
    }

    @Override
    public void addBlog(BlogVo blogVo) {
        HttpServletRequest request = HttpHolder.getRequest();
        // 查看当前推荐博客数量
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Blog::getStatus,EStatusEnum.ENABLE.getValue());
        queryWrapper.eq(Blog::getLevel,blogVo.getLevel());
        Long cnt = blogContentDao.selectCount(queryWrapper);
        // todo: 数量限制
        // 如果是原创，则是作者本人署名
        Blog blog = new Blog();
        setBlogAuthorInfo(blogVo,blog,request);
        // 保存博客
        blog.setContent(blogVo.getContent());
        blog.setTitle(blogVo.getTitle());
        blog.setClickCount(0);
        blog.setTagUid(blogVo.getTagUid());
        blog.setSummary(blogVo.getSummary());
        blog.setBlogSortUid(blogVo.getBlogSortUid());
        blog.setLevel(blogVo.getLevel());
        blog.setIsOriginal(blogVo.getIsOriginal());
        blog.setIsPublish(blogVo.getIsPublish());
        blog.setType(blogVo.getType());
        blog.setFileUid(blogVo.getFileUid());
        blog.setOutsideLink(BlogTypeEnum.PROMOTE.getValue().equals(blogVo.getType())?blogVo.getOutsideLink():"");
        blogContentDao.insert(blog);
    }

    @Override
    public void editBlog(BlogVo blogVo) {
        HttpServletRequest request = HttpHolder.getRequest();
        Blog blog = blogContentDao.selectById(blogVo.getUid());
        // 查看当前推荐博客数量
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Blog::getStatus,EStatusEnum.ENABLE.getValue());
        queryWrapper.eq(Blog::getLevel,blogVo.getLevel());
        Long cnt = blogContentDao.selectCount(queryWrapper);
        if (blog!=null){
            if (!blogVo.getLevel().equals(blog.getLevel())){
                cnt++;
            }
        } else{
            log.info("博客不存在!");
            return;
        }
        // todo:验证等级数量限制
        setBlogAuthorInfo(blogVo,blog,request);
        blog.setUid(blogVo.getUid());
        blog.setContent(blogVo.getContent());
        blog.setTitle(blogVo.getTitle());
        blog.setClickCount(0);
        blog.setTagUid(blogVo.getTagUid());
        blog.setSummary(blogVo.getSummary());
        blog.setBlogSortUid(blogVo.getBlogSortUid());
        blog.setLevel(blogVo.getLevel());
        blog.setIsOriginal(blogVo.getIsOriginal());
        blog.setIsPublish(blogVo.getIsPublish());
        blog.setType(blogVo.getType());
        blog.setFileUid(blogVo.getFileUid());
        blog.setOpenComment(blogVo.getOpenComment());
        blogContentDao.updateById(blog);
    }

    @Override
    public void deleteBlog(BlogVo blogVo) {
        blogContentDao.deleteById(blogVo.getUid());
    }

    @Override
    public void deleteBatch(List<BlogVo> blogVoList) {
        List<String> uids = blogVoList.stream().map(BlogVo::getUid).collect(Collectors.toList());
        blogContentDao.deleteBatchIds(uids);
    }


    private void setBlog(List<Blog> info){
        for (Blog blog : info) {
            setBlogSort(blog);
            setBlogTag(blog);
            setBlogPicture(blog);
        }
    }

    private void setBlogAuthorInfo(BlogVo blogVo,Blog blog,HttpServletRequest request){
        String projectName = sysParamsService.getSysParamValueByKey(Constants.PROJECT_NAME);
        if (request.getAttribute(Constants.ADMIN_UID) == null) {
            log.info("未拿到请求中adminUid的信息!");
            return;
        }
        Admin admin = adminService.getOneAdmin(request.getAttribute(Constants.ADMIN_UID).toString());
        blog.setAdminUid(admin.getUid());
        if (EOriginalEnum.ORIGINAL.getValue().equals(blogVo.getIsOriginal())){
            if (admin!=null){
                if (!StringUtils.isEmpty(admin.getNickName())){
                    blog.setAuthor(admin.getNickName());
                } else{
                    blog.setAuthor(admin.getUserName());
                }
                blog.setArticlesPart(projectName);
            }
        } else{
            blog.setAuthor(blogVo.getAuthor());
            blog.setArticlesPart(projectName);
        }
    }
}
