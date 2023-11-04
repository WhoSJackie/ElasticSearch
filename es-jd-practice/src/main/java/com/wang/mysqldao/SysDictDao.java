package com.wang.mysqldao;

import com.wang.pojo.mysql.SysDictItemPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysDictDao {

    Boolean insert(@Param("item") SysDictItemPojo pojo);

}
