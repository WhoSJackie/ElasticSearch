package com.wang.business.mysqldao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wang.common.object.entity.Blog;
import com.wang.common.object.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDao extends BaseMapper<User> {

    IPage<Blog> queryBlogByLevel(@Param("level") Integer level);


}
