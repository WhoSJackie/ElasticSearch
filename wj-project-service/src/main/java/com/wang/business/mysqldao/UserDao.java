package com.wang.business.mysqldao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.business.pojo.mysql.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao extends BaseMapper<User> {




}
