package com.wang.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.business.mysqldao.BlogContentDao;
import com.wang.business.service.BlogService;
import com.wang.business.service.BlogSortService;
import com.wang.business.service.TagService;
import com.wang.common.constants.RedisConst;
import com.wang.common.feign.FileFeignClient;
import com.wang.common.object.entity.Blog;
import com.wang.business.service.BlogContentService;
import com.wang.common.enums.BlogLevelEnum;
import com.wang.common.utils.IpUtils;
import com.wang.common.utils.RedisUtil;
import com.wang.common.utils.RequestHolder;
import com.wang.common.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlogContentServiceImpl implements BlogContentService {

    @Autowired
    private BlogContentDao blogContentDao;

    @Autowired
    private FileFeignClient fileFeignClient;

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogSortService blogSortService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public IPage<Blog> selectBlogPageByLevel(Integer level, Integer useSort, Long currentPage) {
        // todo: 加上redis缓存，能减少查库频率
        Page<Blog> page = new Page<>();
        page.setCurrent(currentPage);
        int levelCnt = 0;
        // 根据不同的推荐等级，设置不同的数量(后续改成系统参数)
        switch (BlogLevelEnum.getEnumByValue(level)){
            case LEVEL_ONE:
                levelCnt=3;
                break;
            case LEVEL_TWO:
                levelCnt = 5;
                break;
            case LEVEL_THREE:
                levelCnt = 8;
                break;
            case LEVEL_FOUR:
                levelCnt = 12;
                break;
        }
        page.setSize(levelCnt);
        IPage<Blog> res = blogContentDao.queryBlogContentByLevel(page, level);
        // 设置博客图片，标签，分级等详细内容，目前查出来的是id
        List<Blog> blogList = res.getRecords();
        setBlog(blogList);
        res.setRecords(blogList);
        return res;
    }

    @Override
    public Blog queryBlogByUid(String uid, Integer oid) {
        HttpServletRequest request = RequestHolder.getRequest();
        String ip = IpUtils.getIpAddr(request);
        LambdaQueryWrapper<Blog> queryWrapper = new LambdaQueryWrapper<>();
        if (!StrUtils.isEmpty(uid)){
            queryWrapper.eq(Blog::getUid,uid);
        } else{
            queryWrapper.eq(Blog::getOid,oid);
        }
        queryWrapper.last("limit 1");
        Blog blog = blogContentDao.selectOne(queryWrapper);
        List<Blog> blogList = new ArrayList<>();
        blogList.add(blog);
        setBlog(blogList);

        // 根据redis记录点击次数
        String s = redisUtil.get(RedisConst.USER_CLICK + ip + "#"+blog.getUid());
        if (StrUtils.isEmpty(s)){
            // 增加点击数
            int cnt = blog.getClickCount()+1;
            blog.setClickCount(cnt);
            blogContentDao.updateById(blog);
        }
        return blog;
    }


    /**
     * 设置图片，标签，分类信息等
     * @param info
     * @return
     */
    private void setBlog(List<Blog> info){
        for (Blog blog : info) {
            blogService.setBlogSort(blog);
            blogService.setBlogTag(blog);
            blogService.setBlogPicture(blog);
        }
    }
}
