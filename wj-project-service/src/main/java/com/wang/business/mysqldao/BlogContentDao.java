package com.wang.business.mysqldao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wang.common.object.entity.Blog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogContentDao extends BaseMapper<Blog> {

    IPage<Blog> queryBlogContentByLevel(IPage<Blog>page,@Param("level") Integer level);

}
