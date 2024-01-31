package com.wang.business.mysqldao;

import com.wang.business.pojo.mysql.SysDictItemPojo;
import com.wang.business.pojo.oracle.SysDictValuePojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysDictDao {

    Boolean insert(@Param("item") SysDictItemPojo pojo);

    List<SysDictItemPojo> querySysDictValue(@Param("item") SysDictItemPojo sysDictItemPojo);


}
