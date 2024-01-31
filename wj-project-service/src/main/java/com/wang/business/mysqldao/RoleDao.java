package com.wang.business.mysqldao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.business.pojo.mysql.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends BaseMapper<Role> {
    Role queryPojo(@Param("id") Long id);


}
