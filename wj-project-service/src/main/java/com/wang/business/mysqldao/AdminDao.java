package com.wang.business.mysqldao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.business.pojo.mysql.Admin;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminDao extends BaseMapper<Admin> {

    List<Admin> queryItems();

}
